package loulan.com.util;

import org.csource.fastdfs.*;

public class FastDFSClient {

    private TrackerClient trackerClient;
    private TrackerServer trackerServer;
    private StorageClient storageClient;

    /**
     * 构造器，初始化文件上传客户端
     *
     * @param trackerConfFilePath 追踪服务器的配置信息文件
     */
    public FastDFSClient(String trackerConfFilePath) throws Exception {

        if (trackerConfFilePath.contains("classpath:")) {
            trackerConfFilePath = trackerConfFilePath.replace("classpath:", this.getClass().getResource("/").getPath());
        }

        ClientGlobal.init(trackerConfFilePath);

        trackerClient = new TrackerClient();
        trackerServer = trackerClient.getConnection();
        storageClient = new StorageClient(trackerServer, null);
    }

    /**
     * 上传文件
     *
     * @param  fileBuff       文件字节流
     * @param  fileExtension  文件后缀，如jpg、gif
     * @return                完整可访问路径
     */
    public String uploadFile(byte[] fileBuff, String fileExtension) throws Exception {
        String resultUrl = "";
        String[] upload_file = storageClient.upload_file(fileBuff, fileExtension, null);

        if (upload_file != null && upload_file.length > 1) {
            //获取存储服务器信息
            String groupName = upload_file[0];
            String filename = upload_file[1];
            ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer, groupName, filename);

            //组合可以访问的路径
            resultUrl = "http://" + serverInfos[0].getIpAddr() + "/" + groupName + "/" + filename;
        }

        return resultUrl;
    }
}
