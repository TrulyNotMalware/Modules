package dev.notypie.jwt.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile({"jwt","oauth"})
public class RSAVerifier implements JwtVerifier{

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    // FIXME Fix Signature verify.
    @Override
    public boolean verifySignature(String plainText, String signature){
        try{
            Signature mySignature = Signature.getInstance("SHA256withRSA");
            mySignature.initVerify(this.publicKey);
            mySignature.update(plainText.getBytes());
            if(!mySignature.verify(Base64.getDecoder().decode(signature)))
                throw new RuntimeException("Signature invalid.");
        }catch(NoSuchAlgorithmException | InvalidKeyException | SignatureException e){
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public String sign(String plainText) {
        try{
            Cipher encrypt = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            encrypt.init(Cipher.ENCRYPT_MODE, this.privateKey);// Encrypt by this private key.
            return Base64.getEncoder().encodeToString(encrypt.doFinal(plainText.getBytes(StandardCharsets.UTF_8)));
        }catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
//        try{
//            Signature privateSignature = Signature.getInstance("SHA256withRSA");
//            privateSignature.initSign(this.privateKey);
//            privateSignature.update(plainText.getBytes());
//            return Base64.getEncoder().encodeToString(privateSignature.sign());
//        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
//            throw new RuntimeException(e);
//        }
    }

    public String decrypt(String encryptedMessage){
        try{
            Cipher decrypt = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            decrypt.init(Cipher.DECRYPT_MODE, this.publicKey);
            return Base64.getEncoder().encodeToString(decrypt.doFinal(Base64.getDecoder().decode(encryptedMessage)));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
