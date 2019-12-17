package com.sexyuncle.springboot.hikari.service;

import com.loserico.orm.dao.EntityOperations;
import com.sexyuncle.springboot.hikari.entity.EmojiUtf8;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * Copyright: (C), 2019/12/13 15:55
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@Service
@Transactional
public class EmojiService {
	
	@Autowired
	private EntityOperations entityOperations;
	
	public EmojiUtf8 saveEmojiContent(EmojiUtf8 emojiUtf8) {
		return entityOperations.save(emojiUtf8);	
	}
	
	public EmojiUtf8 getEmojiContent(Long id) {
		return entityOperations.get(EmojiUtf8.class, id);
	}
}
