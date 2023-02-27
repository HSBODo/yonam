package net.botmoa.yonam.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class SmsDto {
    private final String host ="www.sendgo.co.kr";
    private final int port = 80;
    private final String path="/Remote/RemoteMms.html";
    private final String remoteId="makebot1";
    private final String remotePwd="make0105!@";
    private String remotePhone;
    private final String remoteCallback="0220392626";
    private String msg;

    public SmsDto(String remotePhone, String msg) {
        this.remotePhone = remotePhone;
        this.msg = msg;
    }
}
