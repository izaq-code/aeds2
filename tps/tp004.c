#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define MAX_SERIES 1000
#define MAX_TITULO 300
#define LINHA_MAX 1000
#define CAMINHO_CSV "disneyplus.csv"
#define CAMINHO_LOG "877487_sequencial.txt"

// Função auxiliar: trim em C (remove espaços no início/fim)
void trim(char *str) {
    // Remove espaços à esquerda
    while(isspace(*str)) memmove(str, str + 1, strlen(str));

    // Remove espaços à direita
    char *fim = str + strlen(str) - 1;
    while(fim > str && isspace(*fim)) *fim-- = '\0';
}

// Função para remover aspas
void removerAspas(char *str) {
    if (str[0] == '"' && str[strlen(str) - 1] == '"') {
        memmove(str, str + 1, strlen(str) - 2);
        str[strlen(str) - 1] = '\0';
    }
}

// Torna a string minúscula (para comparação case-insensitive)
void toLower(char *str) {
    for (int i = 0; str[i]; i++)
        str[i] = tolower(str[i]);
}

int main() {
    FILE *csv = fopen(CAMINHO_CSV, "r");
    if (csv == NULL) {
        perror("Erro ao abrir CSV");
        return 1;
    }

    char linha[LINHA_MAX];
    char *titulos[MAX_SERIES];
    int totalSeries = 0;

    // Ignorar cabeçalho
    fgets(linha, LINHA_MAX, csv);

    // Leitura das séries
    while (fgets(linha, LINHA_MAX, csv) != NULL && totalSeries < MAX_SERIES) {
        char copia[LINHA_MAX];
        strcpy(copia, linha);

        char *campo = strtok(copia, ","); // ID
        for (int i = 0; i < 1; i++) campo = strtok(NULL, ","); // vai para o segundo campo

        campo = strtok(NULL, ","); // terceiro campo = title
        if (campo) {
            trim(campo);
            removerAspas(campo);
            titulos[totalSeries] = malloc(strlen(campo) + 1);
            strcpy(titulos[totalSeries], campo);
            totalSeries++;
        }
    }
    fclose(csv);

    // Parte 1: leitura das entradas (inserir no vetor)
    char entrada[MAX_TITULO];
    char *vetorBusca[MAX_SERIES];
    int totalBusca = 0;

    while (fgets(entrada, MAX_TITULO, stdin)) {
        entrada[strcspn(entrada, "\r\n")] = '\0';
        if (strcmp(entrada, "FIM") == 0) break;

        vetorBusca[totalBusca] = malloc(strlen(entrada) + 1);
        strcpy(vetorBusca[totalBusca], entrada);
        totalBusca++;
    }

    // Parte 2: busca sequencial
    int comparacoes = 0;
    long inicio = clock();

    while (fgets(entrada, MAX_TITULO, stdin)) {
        entrada[strcspn(entrada, "\r\n")] = '\0';
        if (strcmp(entrada, "FIM") == 0) break;

        trim(entrada);
        removerAspas(entrada);
        toLower(entrada);

        int encontrado = 0;
        for (int i = 0; i < totalBusca; i++) {
            char temp[MAX_TITULO];
            strcpy(temp, vetorBusca[i]);
            trim(temp);
            removerAspas(temp);
            toLower(temp);

            if (strcmp(temp, entrada) == 0) {
                encontrado = 1;
                break;
            }
            comparacoes++;
        }

        printf("%s\n", encontrado ? "SIM" : "NAO");
    }

    long fim = clock();
    double tempo = ((double)(fim - inicio)) / CLOCKS_PER_SEC * 1000;

    // Log
    FILE *log = fopen(CAMINHO_LOG, "w");
    if (log) {
        fprintf(log, "877487\t%.0f\t%d\n", tempo, comparacoes);
        fclose(log);
    }

    // Liberar memória
    for (int i = 0; i < totalSeries; i++) free(titulos[i]);
    for (int i = 0; i < totalBusca; i++) free(vetorBusca[i]);

    return 0;
}
