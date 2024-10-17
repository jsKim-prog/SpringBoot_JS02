package org.zerock.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadResultDTO implements Serializable { //Serializable : 직렬화(java객체, 데이터->byte)

    private String fileName;
    private String uuid;
    private String folderPath;

    public String getImageURL() { //전체 경로 필요한 경우 대비
        try {
            return URLEncoder.encode(folderPath + "/" + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getThumbnailURL() {
        try {
            return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}

