package com.arkinmodi.rssreader.discord;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.util.Map;
import javax.security.auth.DestroyFailedException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.pando.crypto.nacl.Crypto;

public class InteractionVerifyTests {

  private static KeyPair signingKeys;

  private InteractionVerify interactionVerify;

  @BeforeAll
  public static void setupClass() {
    signingKeys = Crypto.signingKeyPair();
  }

  @BeforeEach
  public void setup() {
    this.interactionVerify =
        new InteractionVerify(byteArrayToHexString(signingKeys.getPublic().getEncoded()));
  }

  @AfterAll
  public static void afterClass() throws DestroyFailedException {
    signingKeys.getPrivate().destroy();
  }

  private String byteArrayToHexString(byte[] bytes) {
    // https://www.geeksforgeeks.org/java-program-to-convert-byte-array-to-hex-string/
    int len = bytes.length;
    char[] hexValues = "0123456789ABCDEF".toCharArray();
    char[] hexCharacter = new char[len * 2];
    for (int i = 0; i < len; i++) {
      int v = bytes[i] & 0xFF;
      hexCharacter[i * 2] = hexValues[v >>> 4];
      hexCharacter[i * 2 + 1] = hexValues[v & 0x0F];
    }
    return new String(hexCharacter);
  }

  @Test
  public void testVerify() {
    String timestamp = "test timestamp";
    String body = "test body";
    String signature =
        byteArrayToHexString(
            Crypto.sign(
                signingKeys.getPrivate(), (timestamp + body).getBytes(StandardCharsets.UTF_8)));

    assertDoesNotThrow(
        () ->
            interactionVerify.verify(
                Map.of("x-signature-ed25519", signature, "x-signature-timestamp", timestamp),
                body));
  }

  @Test
  public void testVerifyBadSignatureException() {
    String timestamp = "test timestamp";
    String body = "test body";
    String signature =
        byteArrayToHexString(
            Crypto.sign(
                signingKeys.getPrivate(), "different data".getBytes(StandardCharsets.UTF_8)));

    assertThrows(
        InteractionVerify.BadSignatureException.class,
        () ->
            interactionVerify.verify(
                Map.of("x-signature-ed25519", signature, "x-signature-timestamp", timestamp),
                body));
  }

  @Test
  public void testVerifyMissingTimestampHeader() {
    String timestamp = "test timestamp";
    String body = "test body";
    String signature =
        byteArrayToHexString(
            Crypto.sign(
                signingKeys.getPrivate(), (timestamp + body).getBytes(StandardCharsets.UTF_8)));

    assertThrows(
        InteractionVerify.MissingHeaderException.class,
        () -> interactionVerify.verify(Map.of("x-signature-ed25519", signature), body));
  }

  @Test
  public void testVerifyMissingSignatureHeader() {
    String timestamp = "test timestamp";
    String body = "test body";

    assertThrows(
        InteractionVerify.MissingHeaderException.class,
        () -> interactionVerify.verify(Map.of("x-signature-timestamp", timestamp), body));
  }
}
