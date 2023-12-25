package com.larksuite.oapi.composite_api.drive;

import com.lark.oapi.Client;
import com.lark.oapi.service.drive.v1.enums.FileUploadInfoParentTypeEnum;
import com.lark.oapi.service.drive.v1.enums.MediaUploadInfoParentTypeEnum;
import com.lark.oapi.service.drive.v1.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Adler32;


public class Drive {

    private static final Logger log = LoggerFactory.getLogger(Drive.class);

    /**
     * 分片上传大文件至云空间文件夹下，使用到三个OpenAPI：
     * 1. [分片上传文件（预上传）](<a href="https://open.feishu.cn/document/server-docs/docs/drive-v1/media/multipart-upload-media/upload_prepare">...</a>)
     * 2. [分片上传文件（上传分片）](<a href="https://open.feishu.cn/document/server-docs/docs/drive-v1/media/multipart-upload-media/upload_part">...</a>)
     * 2. [分片上传文件（完成上传）](<a href="https://open.feishu.cn/document/server-docs/docs/drive-v1/media/multipart-upload-media/upload_finish">...</a>)
     */
    public static UploadFinishMediaResp uploadMediaByPart(Client client, String parentNode, File file, MediaUploadInfoParentTypeEnum type) throws Exception {
        // 预上传, 获取文件分片大小与分片数量
        UploadPrepareMediaResp prepareResp = client.drive()
            .media()
            .uploadPrepare(
                UploadPrepareMediaReq.newBuilder()
                    .mediaUploadInfo(
                        MediaUploadInfo.newBuilder()
                            .fileName(file.getName())
                            .size(toInt(file.length()))
                            .parentNode(parentNode)
                            .parentType(type)
                            .build()
                    )
                    .build()
            );

        UploadPrepareMediaRespBody prepareRespData = prepareResp.getData();
        log.info("[upload-prepare-resp] upload-id={}, block-size:{}, block-num: {}",
            prepareRespData.getUploadId(),
            prepareRespData.getBlockSize(),
            prepareRespData.getBlockNum()
        );

        // 拆分文件分片, 逐片上传
        Map<Integer, File> sliceFileMap = sliceFile(file, prepareRespData.getBlockSize(), prepareRespData.getBlockNum());
        for (int i = 0; i < prepareRespData.getBlockNum(); i++) {
            File sliceFile = sliceFileMap.get(i);
            UploadPartMediaResp resp = client.drive()
                .media()
                .uploadPart(
                    UploadPartMediaReq.newBuilder()
                        .uploadPartMediaReqBody(
                            UploadPartMediaReqBody.newBuilder()
                                .uploadId(prepareRespData.getUploadId())
                                .size(toInt(sliceFile.length()))
                                .seq(i)
                                .checksum(calculateAdler32(sliceFile))
                                .file(sliceFile)
                                .build()
                        )
                        .build()
                );

            log.info("[upload-part-resp] index={}, success={}, msg={}", i, resp.success(), resp.getMsg());
        }

        // 清理碎片文件
        sliceFileMap.values().forEach(Drive::del);

        // 完成上传
        UploadFinishMediaResp finishResp = client.drive()
            .media()
            .uploadFinish(
                UploadFinishMediaReq.newBuilder()
                    .uploadFinishMediaReqBody(
                        UploadFinishMediaReqBody.newBuilder()
                            .uploadId(prepareRespData.getUploadId())
                            .blockNum(prepareRespData.getBlockNum())
                            .build()
                    )
                    .build()
            );

        log.info("[upload-finish-resp] success={}, msg={}, fileToken={}",
            finishResp.success(), finishResp.getMsg(), finishResp.getData().getFileToken());
        return finishResp;
    }

    /**
     * 分片上传大文件至云空间文件夹下，使用到三个OpenAPI：
     * 1. [分片上传文件（预上传）](<a href="https://open.feishu.cn/document/server-docs/docs/drive-v1/upload/multipart-upload-file-/upload_prepare">...</a>)
     * 2. [分片上传文件（上传分片）](<a href="https://open.feishu.cn/document/server-docs/docs/drive-v1/upload/multipart-upload-file-/upload_part">...</a>)
     * 2. [分片上传文件（完成上传）](<a href="https://open.feishu.cn/document/server-docs/docs/drive-v1/upload/multipart-upload-file-/upload_finish">...</a>)
     */
    public static UploadFinishFileResp uploadFileByPart(Client client, String folderToken, File file) throws Exception {
        // 预上传, 获取文件分片大小与分片数量
        UploadPrepareFileResp prepareResp = client.drive()
            .file()
            .uploadPrepare(
                UploadPrepareFileReq.newBuilder()
                    .fileUploadInfo(
                        FileUploadInfo.newBuilder()
                            .fileName(file.getName())
                            .size(toInt(file.length()))
                            .parentNode(folderToken)
                            .parentType(FileUploadInfoParentTypeEnum.EXPLORER)
                            .build()
                    )
                    .build()
            );

        UploadPrepareFileRespBody prepareRespData = prepareResp.getData();
        log.info("[upload-prepare-resp] upload-id={}, block-size:{}, block-num: {}",
            prepareRespData.getUploadId(),
            prepareRespData.getBlockSize(),
            prepareRespData.getBlockNum()
        );

        // 拆分文件分片, 逐片上传
        Map<Integer, File> sliceFileMap = sliceFile(file, prepareRespData.getBlockSize(), prepareRespData.getBlockNum());
        for (int i = 0; i < prepareRespData.getBlockNum(); i++) {
            File sliceFile = sliceFileMap.get(i);
            UploadPartFileResp resp = client.drive()
                .file()
                .uploadPart(
                    UploadPartFileReq.newBuilder()
                        .uploadPartFileReqBody(
                            UploadPartFileReqBody.newBuilder()
                                .uploadId(prepareRespData.getUploadId())
                                .size(toInt(sliceFile.length()))
                                .seq(i)
                                .checksum(calculateAdler32(sliceFile))
                                .file(sliceFile)
                                .build()
                        )
                        .build()
                );

            log.info("[upload-part-resp] index={}, success={}, msg={}", i, resp.success(), resp.getMsg());
        }

        // 清理碎片文件
        sliceFileMap.values().forEach(Drive::del);

        // 完成上传
        UploadFinishFileResp finishResp = client.drive()
            .file()
            .uploadFinish(
                UploadFinishFileReq.newBuilder()
                    .uploadFinishFileReqBody(
                        UploadFinishFileReqBody.newBuilder()
                            .uploadId(prepareRespData.getUploadId())
                            .blockNum(prepareRespData.getBlockNum())
                            .build()
                    )
                    .build()
            );

        log.info("[upload-finish-resp] success={}, msg={}, fileToken={}",
            finishResp.success(), finishResp.getMsg(), finishResp.getData().getFileToken());
        return finishResp;
    }

    /**
     * 数据类型转换, 上传需要文件的长度为int类型
     *
     * @param length 文件长度
     * @return int类型文件长度
     */
    private static Integer toInt(long length) {
        return ((Long) length).intValue();
    }

    /**
     * 根据预上传接口,切分文件
     *
     * @param file      原始文件
     * @param blockSize 分片大小
     * @param blockNum  分片数量
     * @return 分片后的文件集合
     */
    private static Map<Integer, File> sliceFile(File file, Integer blockSize, Integer blockNum) {
        Map<Integer, File> sliceFileMap = new HashMap<>(blockNum);
        String tempDir = System.getProperty("java.io.tmpdir");
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            byte[] buffer = new byte[blockSize];
            int bytesRead;
            int partNumber = 0;
            while ((bytesRead = bis.read(buffer)) > 0) {
                String fileName = file.getName() + ".part" + String.format("%03d", partNumber);
                File outputFile = new File(tempDir, fileName);
                try (FileOutputStream fos = new FileOutputStream(outputFile);
                     BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                    bos.write(buffer, 0, bytesRead);
                }

                sliceFileMap.put(partNumber, outputFile);
                partNumber++;
            }
        } catch (IOException e) {
            log.warn("{}", e.getMessage());
        }

        if (sliceFileMap.size() != blockNum) {
            log.warn("文件分片数量错误, 删除所有分片");
            sliceFileMap.values().forEach(Drive::del);
            sliceFileMap.clear();
        }

        return sliceFileMap;
    }

    /**
     * 计算上传文件的Adler32值做校验
     *
     * @param file 分片文件
     * @return Adler32值
     * @throws IOException IO异常
     */
    private static String calculateAdler32(File file) throws IOException {
        Adler32 adler32 = new Adler32();
        adler32.update(readBytes(file));
        return ((Long) adler32.getValue()).toString();
    }

    /**
     * 删除文件
     *
     * @param file 文件
     */
    private static void del(File file) {
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            log.warn("{}", e.getMessage());
        }
    }

    /**
     * 读取文件所有数据<br>
     * 文件的长度不能超过 {@link Integer#MAX_VALUE}
     *
     * @return 字节码
     * @throws IOException IO异常
     */
    private static byte[] readBytes(File file) throws IOException {
        long len = file.length();
        if (len >= Integer.MAX_VALUE) {
            throw new IOException("File is larger then max array size");
        }

        byte[] bytes = new byte[(int) len];
        int readLength;
        try (FileInputStream in = new FileInputStream(file)) {
            readLength = in.read(bytes);
            if (readLength < len) {
                throw new IOException("File length is " + len + " but read " + readLength + "!");
            }
        } catch (Exception e) {
            throw new IOException(e);
        }

        return bytes;
    }
}
