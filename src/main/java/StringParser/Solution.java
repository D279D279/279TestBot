package StringParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) throws IOException {
        String article = "@d279sadf firstName(12) lastName_2134 (123456789):\nHello, Bot!";
        System.out.println(article);

        BufferedReader reader = new BufferedReader(new StringReader(article));
        String userInfo = "";
        if(reader.ready()) {
            userInfo = reader.readLine();
            reader.close();
        }
        System.out.println(userInfo + "\n\n");

        Scanner scanner = new Scanner(userInfo);
        String chatId = "";
        while (scanner.hasNext()) {
            chatId = scanner.next();
        }
        scanner.close();;

        chatId = chatId.substring(1, chatId.length() - 2);
        System.out.println(chatId);



    }
}
