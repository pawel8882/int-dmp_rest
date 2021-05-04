package test.intdmp.core.model.files._enum;

public enum FileType {
    DWG("pi pi-file"),
    PDF("pi pi-file"),
    TXT("pi pi-file");

    private final String icon;

    FileType(String icon) {
        this.icon = icon;
    }

    public String getIcon(){
        return this.icon;
    }

}
