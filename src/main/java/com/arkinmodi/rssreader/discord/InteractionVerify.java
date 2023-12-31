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

  public void verify(Map<String, String> headers, String body)
      throws BadSignatureException, MissingHeaderException {
    final String signature = headers.get("x-signature-ed25519");
    final String timestamp = headers.get("x-signature-timestamp");

    if (signature == null) {
      throw new MissingHeaderException("Missing x-signature-ed25519 Header");
    } else if (timestamp == null) {
      throw new MissingHeaderException("Missing x-signature-timestamp Header");
    }

    final byte[] data = (timestamp + body).getBytes(StandardCharsets.UTF_8);
    if (!Crypto.signVerify(publicKey, data, hexStringToByteArray(signature))) {
      throw new BadSignatureException("Invalid Request Signature");
    }
  }

  private byte[] hexStringToByteArray(String s) {
    // https://www.geeksforgeeks.org/java-program-to-convert-hex-string-to-byte-array/
    final int len = s.length();
    final byte[] data = new byte[len / 2];
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
  }

  public class MissingHeaderException extends Exception {
    MissingHeaderException(String msg) {
      super(msg);
    }
  }
}
