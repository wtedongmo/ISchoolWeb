/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tsoft.service.process;


import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public abstract class EmptyReportProcess extends EmptyActionProcess {
    public EmptyReportProcess() {
    }

    public ResponseEntity<byte[]> download(String pathfile) throws IOException {
        Resource resource = this.resourceLoader.getResource("file:" + pathfile);
        InputStream in = resource.getInputStream();

        ResponseEntity var5;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentLength(resource.contentLength());
            headers.add("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"");
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            var5 = new ResponseEntity(IOUtils.toByteArray(in), headers, HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }

        return var5;
    }
}
