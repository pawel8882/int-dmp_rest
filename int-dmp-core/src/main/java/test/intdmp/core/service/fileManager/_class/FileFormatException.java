package test.intdmp.core.service.fileManager._class;

import test.intdmp.core.model.files._enum.UploadStatus;

public class FileFormatException extends IllegalArgumentException {

    private final String detailedMessage;

    public FileFormatException(UploadStatus status) {
        this.detailedMessage = status.getErrorDescription();
    }

    @Override
    public String getMessage() {
        return this.detailedMessage;
    }
}
