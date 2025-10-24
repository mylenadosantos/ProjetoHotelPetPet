package Util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Logger {
	
	// Define o formato para as horas e datas (ex: 2025-10-24 15:30:00)
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    // Define o nome do arquivo que vai guardar o histórico (na raiz do projeto)
    private static final String LOG_FILE = "log.txt"; 


    public static void log(String nivel, String mensagem) {
        // Pega a hora exata agora para registrar o momento do evento
        String timestamp = LocalDateTime.now().format(FORMATTER);
        
        // Monta a linha completa para o registro (padrão: [HORA] [NÍVEL] MENSAGEM)
        String logLine = "[" + timestamp + "] [" + nivel + "] " + mensagem;

        // NÃO IMPRIMIMOS NO CONSOLE AQUI para manter a saída do usuário limpa.
        
        // Chama a função que escreve a mensagem no arquivo de histórico
        writeLogToFile(logLine);
    }
    
    // Função auxiliar faz o trabalho pesado de escrever a linha de log no arquivo.
     
    private static void writeLogToFile(String logLine) {
        // O 'try-with-resources' garante que o arquivo será fechado corretamente, mesmo se der erro.
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            
            pw.println(logLine);
            
        } catch (IOException e) {
            // Em caso de erro CRÍTICO ao escrever no histórico, SOMENTE AQUI avisamos no console.
            System.err.println("Erro CRÍTICO: Falha ao escrever no arquivo de log: " + e.getMessage());
        }
    }
}
