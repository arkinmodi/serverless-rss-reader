package com.arkinmodi.rssreader.discord;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Map;
import software.pando.crypto.nacl.Crypto;

public class InteractionVerify {

  private final PublicKey publicKey;

  public InteractionVerify(String publicKey) {
    this.publicKey = Crypto.signingPublicKey(hexStringToByteArray(publicKey));
  }

  public void verify(Map<String, String> headers, String body) throws BadSignatureException {
    String signature = headers.get("X-Signature-Ed25519");
    String timestamp = headers.get("X-Signature-Timestamp");

    byte[] data = (timestamp + body).getBytes(StandardCharsets.UTF_8);
    if (!Crypto.signVerify(this.publicKey, data, hexStringToByteArray(signature))) {
      throw new BadSignatureException("Invalid Request Signature");
    }
  }

  private byte[] hexStringToByteArray(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] =
          (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }

  public class BadSignatureException extends Exception {
    BadSignatureException(String msg) {
      super(msg);
    }

    BadSignatureException(String msg, Throwable err) {
      super(msg, err);
    }
  }
}
