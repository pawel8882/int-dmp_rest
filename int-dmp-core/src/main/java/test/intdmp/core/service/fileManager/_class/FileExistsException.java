package test.intdmp.core.service.fileManager._class;

import test.intdmp.core.model.files._enum.UploadStatus;

import java.io.IOException;

public class FileExistsException extends IOException {


    private final String detailedMessage;

    public FileExistsException(UploadStatus status) {
        this.detailedMessage = status.getErrorDescription();
    }

    @Override
    public String getMessage() {
        return this.detailedMessage;
    }
}
