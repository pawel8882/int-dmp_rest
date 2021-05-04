package test.intdmp.core.service.fileManager._class;
import test.intdmp.core.model.files._enum.UploadStatus;

public class FileNameErrorException extends Throwable {

    private String detailedMessage;

    public FileNameErrorException(UploadStatus status) {
        this.detailedMessage = status.getErrorDescription();
    }

    public String getMessage() {
        return this.detailedMessage;
    }

}
