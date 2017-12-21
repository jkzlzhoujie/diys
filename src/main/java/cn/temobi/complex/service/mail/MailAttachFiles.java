package cn.temobi.complex.service.mail;

import javax.activation.FileDataSource;

public class MailAttachFiles {
    private FileDataSource attachFileDataSource;
    private String attachFileName;

    public FileDataSource getAttachFileDataSource() {
        return attachFileDataSource;
    }

    public void setAttachFileDataSource(FileDataSource attachFileDataSource) {
        this.attachFileDataSource = attachFileDataSource;
    }

    public String getAttachFileName() {
        return attachFileName;
    }

    public void setAttachFileName(String attachFileName) {
        this.attachFileName = attachFileName;
    }
}
