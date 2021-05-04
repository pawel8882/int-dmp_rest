package test.intdmp.core.service.fileManager._class;
import test.intdmp.core.model.files._enum.FileType;
import test.intdmp.core.model.files._enum.UploadStatus;

public class FileStatus {
    public String fileStatus;
    public String fileName;

    public FileStatus(String status, String fileName) {
        this.fileStatus = status;
        this.fileName = fileName;
    }

    public FileStatus(UploadStatus status, String fileName) {
        this.fileStatus = status.getErrorDescription();
        this.fileName = fileName;
    }
}
