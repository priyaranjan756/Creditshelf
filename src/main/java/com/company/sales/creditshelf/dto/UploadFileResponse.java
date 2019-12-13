package com.company.sales.creditshelf.dto;

public class UploadFileResponse {
    private String fileName;
    private String fileType;
 

    public UploadFileResponse(String fileName, String fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
    }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}