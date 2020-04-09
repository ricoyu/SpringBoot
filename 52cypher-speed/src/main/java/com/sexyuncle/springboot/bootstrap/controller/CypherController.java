package com.sexyuncle.springboot.bootstrap.controller;

import com.loserico.codec.Rsa;
import com.loserico.codec.RsaUtils;
import com.loserico.codec.RsaUtils.RsaKeyPair;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Copyright: (C), 2019/12/7 21:11
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RestController
@RequestMapping
public class CypherController {
	
	private RsaKeyPair rsaKeyPair;
	private Cipher cipher;
	
	@PostConstruct
	public void init() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException {
		this.rsaKeyPair = RsaUtils.initRSAKey(2048);
		
		// 对密钥解密
		byte[] keyBytes = Base64.decodeBase64(rsaKeyPair.privateKey());
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(Rsa.ALGORITHM_RSA);
		Key key = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, key);
		this.cipher = cipher;
	}
	
	@GetMapping("/public-key")
	public String publicKey() {
		return rsaKeyPair.publicKey();
	}
	
	@PostMapping("/decrypt")
	public Map decript(String encrypted) {
		long begin = System.currentTimeMillis();
		String decrypted = RsaUtils.privateDecrypt(encrypted, rsaKeyPair.privateKey());
		long end = System.currentTimeMillis();
		Map<String, Object> result = new HashMap<>();
		result.put("time", (end - begin));
		result.put("decrypted", decrypted);
		return result;
	}
	
	@PostMapping("/decrypt-cached")
	public Map decriptCached(String encrypted) throws BadPaddingException, IllegalBlockSizeException {
		long begin = System.currentTimeMillis();
		byte[] bytes = cipher.doFinal(Base64.decodeBase64(encrypted));
		String decrypted = new String(bytes, StandardCharsets.UTF_8);
		long end = System.currentTimeMillis();
		Map<String, Object> result = new HashMap<>();
		result.put("time", (end - begin));
		result.put("decrypted", decrypted);
		return result;
	}
}
