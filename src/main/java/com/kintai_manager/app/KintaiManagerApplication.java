package com.kintai_manager.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class KintaiManagerApplication {

	public static void main(String[] args) {
		// 環境変数を読み込む
		Dotenv environment_variables = Dotenv.configure().filename("local.env").ignoreIfMissing().load();

		// 読み込んだ環境変数をシステムプロパティに設定する
		environment_variables.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(KintaiManagerApplication.class, args);
	}

}
