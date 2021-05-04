package test.intdmp.core.model.files._enum;

public enum UploadStatus {
    OK("OK"),
    DEPARTMENT_ERROR("Department number not found."),
    DEPARTMENT_ERROR_TOOMANY("Found more than one department with given number."),
    SECTIONDEPARTMENT_ERROR("Error in section department number."),
    PROJECT_ERROR("Error in project number."),
    REVISION_ERROR("Revision already exist."),
    DRAWINGNUMBER_ERROR("Drawing number already exist."),
    DRAWINGNAME_ERROR("Error in drawing name."),
    DRAWINGTYPE_ERROR("Drawing type is invalid."),
    FILE_EXIST("File already exists."),
    FILE_FORMAT_ERROR("File format is not recognized.");

    private final String comment;

    UploadStatus(String s) {
        this.comment = s;
    }

    public String getErrorDescription(){
        return this.comment;
    }
}
