package ru.tilipod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

@SpringBootApplication(exclude = {MultipartAutoConfiguration.class })
public class UserWorkProjectApplication {

	/**
	 * Бин для чтения загружаемых файлов
	 * @author Tilipod
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipart = new CommonsMultipartResolver();
		multipart.setMaxUploadSize(DataSize.parse("10MB").toBytes());

		// Эта настройка позволяет держать большие файлы (до 10 Мб) в памяти даже после завершения запроса
		// Требуется, чтобы загруженный файл не был стерт после вызова выполнения Callback
		multipart.setMaxInMemorySize((int) DataSize.parse("10MB").toBytes());
		return multipart;
	}

	/**
	 * Бин для распознавания пришедших на сервер файлов
	 * @author Tilipod
	 */
	@Bean
	public MultipartFilter multipartFilter() {
		MultipartFilter multipartFilter = new MultipartFilter();
		multipartFilter.setMultipartResolverBeanName("multipartResolver");
		return multipartFilter;
	}

	public static void main(String[] args) {
		SpringApplication.run(UserWorkProjectApplication.class, args);
	}

}
