# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## プロジェクト概要

Spring Boot 3.5.7を使用した勤怠管理システム（KintaiManager）。打刻データ（出勤・退勤・休憩開始・休憩終了）を管理し、月次勤怠表を生成する。

## 技術スタック

- Java 17
- Spring Boot 3.5.7
- Spring Data JPA + JDBC
- Thymeleaf（テンプレートエンジン）
- MySQL
- Lombok
- Maven
- dotenv-java（環境変数管理）

## 開発コマンド

### ビルドとテスト
```bash
# プロジェクトのビルド
./mvnw clean package

# テストの実行
./mvnw test

# 特定のテストクラスを実行
./mvnw test -Dtest=KintaiManagerApplicationTests

# アプリケーションの起動
./mvnw spring-boot:run
```

### 開発時の注意点
- DB接続情報は`local.env`ファイルで管理（このファイルは絶対に開かないこと）
- `application.properties`では`${DB_URL}`, `${DB_USERNAME}`, `${DB_PASSWORD}`として環境変数を参照
- アプリケーション起動時に`dotenv-java`が`local.env`を読み込み、システムプロパティに設定

## アーキテクチャ

### レイヤ構成

標準的なSpring MVCの3層アーキテクチャ:

```
Controller → Service → Repository → DB
    ↓
  View (Thymeleaf)
```

### パッケージ構成

- `com.kintai_manager.app.controller`: HTTPリクエストのハンドリング
  - `AttendanceController`: 打刻登録
  - `AttendanceSheetController`: 月次勤怠表
  - `TopPageController`: トップページ
  - `GlobalExceptionHandler`: グローバル例外処理
- `com.kintai_manager.app.service`: ビジネスロジック
  - `RegistService`: 打刻登録処理
  - `CreateMonthlyCalendarService`: 月次カレンダー生成
  - `service.validation`: カスタムバリデーション
- `com.kintai_manager.app.repository`: データアクセス層
  - Spring Data JPAとカスタムクエリを併用
- `com.kintai_manager.app.entity`: JPAエンティティ
  - `TimeEvent`: 打刻イベントテーブル（複合主キー使用）
  - `TimeEventPrimaryKey`: 複合主キー（`@EmbeddedId`）
- `com.kintai_manager.app.dto`: データ転送オブジェクト
- `com.kintai_manager.app.form`: フォームバリデーション用DTO
- `com.kintai_manager.app.enums`: 列挙型（`EventType`等）
- `com.kintai_manager.app.mock`: モック実装

### 主要なドメインモデル

#### TimeEvent（打刻イベント）

複合主キー構成:
- `event_day` (CHAR(8)): イベント日（YYYYMMDD形式）
- `employee_id` (CHAR(5)): 従業員ID
- `event_type` (VARCHAR): イベント種別（WORK_START, WORK_END, BREAK_START, BREAK_END）
- `repeat_no` (INT): 繰り返し番号（同日同種別の複数打刻を許容）

主要カラム:
- `event_at` (CHAR(12)): 打刻時刻（元データ、YYYYMMDDHHmm形式）
- `rounded_event_at` (CHAR(12)): 丸め処理後の打刻時刻
- `is_correction` (TINYINT): 修正フラグ
- `reason` (VARCHAR): 理由
- 監査フィールド: `created_at`, `created_employee_id`, `updated_at`, `updated_employee_id`

#### EventType列挙型

勤怠イベントの種別を定義:
- `WORK_START`: 出勤
- `WORK_END`: 退勤
- `BREAK_START`: 休憩開始
- `BREAK_END`: 休憩終了

各種別に表示名とカテゴリ（WORK/BREAK）を持つ。

### データベース設計

- `time_event`テーブルの詳細は`README.md`参照
- 複合主キーによる同一日・同一種別の複数打刻対応
- 外部キーで`employee`テーブルを参照（テーブル定義は別途）

### 設定ファイル

- `application.properties`: Spring Boot設定
  - DB接続情報（環境変数参照）
  - カスタムプロパティ: `kintai-event.work.threshold`, `kintai-event.break.threshold`（同一イベント種別の登録許容件数）

### Lombokの使用

プロジェクト全体でLombokを活用:
- `@Data`: エンティティとDTO
- `@RequiredArgsConstructor`: DIコンストラクタ（コントローラ、サービス）

新規クラス作成時も同様のパターンを踏襲すること。
