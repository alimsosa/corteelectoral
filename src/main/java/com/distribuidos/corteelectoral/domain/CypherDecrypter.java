package com.distribuidos.corteelectoral.domain;

import com.distribuidos.corteelectoral.services.CorteElectoralService;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public class CypherDecrypter {

    private static final String RSA_ALGORITHM = "RSA";
    private static KeyFactory keyFactory;
    private static Cipher cipher;

    private static String filePath;
    public static String decrypt(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, InvalidKeySpecException {
        return CorteElectoralService.canDecrypt ? CypherDecrypter.getDecipherTextByPrivateKey(data) : null;
    }
    private static String getDecipherTextByPrivateKey(String data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedBytes = decode(data);
        keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, CypherDecrypter.getPrivateKey());
        byte[] decryptedMessage = cipher.doFinal(encryptedBytes);
        return new String(decryptedMessage, StandardCharsets.UTF_8);
    }
    private static RSAPrivateKey getPrivateKey() throws IOException, InvalidKeySpecException {

        String privKeyFile = readFile("C:\\private_key.pem");
        String privateKeyPEM = getPrivatePem(privKeyFile);

        byte[] encodedPrivateKeyPEM = decode(privateKeyPEM);
        System.out.println(new String(privateKeyPEM));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPrivateKeyPEM);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }
    private static String readFile(String path) throws IOException {
        File file = new File(path);
        String fileText = Files.readString(file.toPath(), Charset.defaultCharset());
        return fileText;
    }

    private static String getPrivatePem(String strFilePriv)
    {
        String privateKeyPEM = strFilePriv
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");
        return privateKeyPEM;
    }
    private static String encode(byte[] data)
    {
        return Base64.getEncoder().encodeToString(data);

    }
    private static byte[] decode(String data)
    {
        return Base64.getDecoder().decode(data);
    }

    public static void setFilePath(String filePath)
    {
        CypherDecrypter.filePath = filePath;
    }
}