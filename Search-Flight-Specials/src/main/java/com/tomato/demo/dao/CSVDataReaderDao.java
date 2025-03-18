package com.tomato.demo.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public abstract class CSVDataReaderDao<T> {

    /**
     * このメソッドは、CSVファイルからデータを読み取り、型Tのオブジェクトのリストを返する。
     * このクラスは抽象クラスとして設計されており、サブクラスが異なる種類のCSVファイルに対して
     * 特定の解析および検証ロジックを定義できるようにする。
     *
     * @param fileName
     * @return CSVファイルから解析された型Tのオブジェクトのリスト
     * @throws IllegalArgumentException
     */
    public List<T> readCSVFileData(String fileName) {
        List<T> dataList = new ArrayList<>(); // CSVファイルサイズに制約がないようにするため空のArrayListを初期化する。

        // リソースが使用後に自動的に閉じられるようにする。
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName); // fileNameをクラスパスから検索し、バイトストリーム形式で返却。 もしファイルが見つからない場合は、nullを返却。

             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) { // バイト単位で読み込んだデータをInputStreamReaderが文字で解析（バイトストリーム->文字ストリーム変換）。Input Stream ReaderオブジェクトをBuffered Readerオブジェクトにラッピングして、ファイルからテキストを一度に一行ずつ読み込む。

            // 入力ストリームがnullであるかどうかを確認する。
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName); // ファイルがクラスパスに見つからない場合、例外をスローする。
            }

            String readLine; // ファイルから読み取られた各行を格納するための変数。
            // whileループを使用してBuffered Readerから一行ずつテキストを読み込む。
            while ((readLine = br.readLine()) != null) { // ファイルの終わり（readLineがnull）に達するまで読み取りを続行する。
                String[] data = readLine.split(","); // 読み込んだテキスト行をコンマ(,)を基準に分離し、CSVファイルから読み込んだラインを文字列配列に保存。

                // サブクラスの実装に基づいて、データが有効かどうかを確認する。
                if (isValidData(data)) {
                    
                    T object = parseData(data); // サブクラスの実装を使用して、データを型Tのオブジェクトに解析する。
                    dataList.add(object); // 解析されたオブジェクトをリストに追加する。
//                    System.out.println("Parsed object: " + object); // デバッグ目的で、解析されたオブジェクトをコンソールに出力する。
                } else {
                    System.out.println("Skipping invalid line: " + readLine); // 無効な行をコンソールに記録する。
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // ファイルの読み取りまたは解析中に発生した例外のスタックトレースを出力する。
        }

        return dataList; // 解析されたオブジェクトのリストを返却する。
    }

    /**
     * 
     * データ配列が有効かどうかを確認する抽象メソッド。
     * 
     *
     * @param data CSVファイルの1行からのデータ（文字列）の配列。コンマ区切り文字で分割されている。
     * @return データがサブクラスの検証ルールに従って有効な場合はtrue、そうでない場合はfalse。
     */
    protected abstract boolean isValidData(String[] data);

    /**
     * 
     * データ配列を型Tのオブジェクトに解析する抽象メソッド。
     * 
     *
     * @param data CSVファイルの1行からのデータ（文字列）の配列。コンマ区切り文字で分割されている。
     * @return データ配列から解析された型Tのオブジェクト。
     */
    protected abstract T parseData(String[] data);
}