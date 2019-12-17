package com.sexyuncle.springboot.hikari.controller;

import com.sexyuncle.springboot.hikari.entity.EmojiUtf8;
import com.sexyuncle.springboot.hikari.service.EmojiService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * Copyright: (C), 2019/12/13 16:02
 * <p>
 * <p>
 * Company: Sexy Uncle Inc.
 *
 * @author Rico Yu ricoyu520@gmail.com
 * @version 1.0
 */
@RestController
@RequestMapping("/emoji")
public class EmojiController {
	
	@Autowired
	private EmojiService emojiService;
	
	@PostMapping("")
	public EmojiUtf8 create(EmojiUtf8 emojiUtf8) {
		String content = EmojiParser.parseToHtmlDecimal(emojiUtf8.getContent());
		emojiUtf8.setContent(content);
		return emojiService.saveEmojiContent(emojiUtf8);
	}
	
	@GetMapping("/{id}")
	public EmojiUtf8 get(@PathVariable  Long id) {
		return emojiService.getEmojiContent(id);
	}
	
	public static void main(String[] args) {
		Object[] values = new Object[]{};
		//Object[] values = null;
		//Object[] values = new Object[]{1, "asd", null, new Object(), LocalDate.now(), "An \uD83D\uDE00awesome \uD83D\uDE03string with a few \uD83D\uDE09emojis!"};
		escapeEmoji(values);
		//for (int i = 0; i < values.length; i++) {
		//	System.out.println(values[i]);
		//}
	}
	
	private static void escapeEmoji(Object[] objArr) {
		if (objArr == null || objArr.length == 0) {
			return;
		}
		for(int i=0; i<objArr.length; i++) {
			Object value = objArr[i];
			if (value != null && (value instanceof String)) {
				value = EmojiParser.parseToHtmlDecimal((String) value);
				objArr[i] = value;
			}
		}
	}
}
