#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>

#define MAX_SHOWS 1400
#define MAX_STRING 500

typedef struct {
    char showId[MAX_STRING];
    char type[MAX_STRING];
    char title[MAX_STRING];
    char director[MAX_STRING];
    char** cast;
    int castSize;
    char country[MAX_STRING];
    char dateAdded[MAX_STRING];
    int releaseYear;
    char rating[MAX_STRING];
    char duration[MAX_STRING];
    char** listedIn;
    int listedInSize;
} Show;

// Funções auxiliares
void strReplace(char* str, char find, char replace) {
    for (char *p = str; *p; p++) {
        if (*p == find) *p = replace;
    }
}

int strToInt(const char* str) {
    int result = 0;
    for (int i = 0; str[i] != '\0'; i++) {
        result = result * 10 + (str[i] - '0');
    }
    return result;
}

void strTrim(char* str) {
    int i = 0, j = 0;
    while (str[i]) {
        if (str[i] != ' ' && str[i] != '\n' && str[i] != '\r' && str[i] != '\t') {
            str[j++] = str[i];
        }
        i++;
    }
    str[j] = '\0';
}

// Funções para manipulação de Show
void showSetShowId(Show* show, const char* value) {
    strcpy(show->showId, (value[0] == '\0') ? "NaN" : value);
}

void showSetType(Show* show, const char* value) {
    strcpy(show->type, (value[0] == '\0') ? "NaN" : value);
}

void showSetTitle(Show* show, const char* value) {
    strcpy(show->title, (value[0] == '\0') ? "NaN" : value);
}

void showSetDirector(Show* show, const char* value) {
    strcpy(show->director, (value[0] == '\0') ? "NaN" : value);
}

void showSetCast(Show* show, const char* value) {
    char temp[MAX_STRING];
    strcpy(temp, value);
    strTrim(temp);
    
    if (temp[0] == '\0') {
        show->castSize = 1;
        show->cast = malloc(sizeof(char*));
        show->cast[0] = strdup("NaN");
        return;
    }

    char* token;
    char* rest = temp;
    int count = 0;
    
    // Contar quantos atores existem
    while ((token = strtok_r(rest, ",", &rest))) {
        count++;
    }
    
    show->castSize = count;
    show->cast = malloc(count * sizeof(char*));
    
    // Resetar o rest para tokenizar novamente
    rest = temp;
    count = 0;
    while ((token = strtok_r(rest, ",", &rest))) {
        strTrim(token);
        show->cast[count] = strdup(token);
        count++;
    }
}

void showSetCountry(Show* show, const char* value) {
    strcpy(show->country, (value[0] == '\0') ? "NaN" : value);
}

void showSetDateAdded(Show* show, const char* value) {
    strcpy(show->dateAdded, (value[0] == '\0') ? "NaN" : value);
}

void showSetReleaseYear(Show* show, const char* value) {
    show->releaseYear = (value[0] == '\0') ? 0 : strToInt(value);
}

void showSetRating(Show* show, const char* value) {
    strcpy(show->rating, (value[0] == '\0') ? "NaN" : value);
}

void showSetDuration(Show* show, const char* value) {
    strcpy(show->duration, (value[0] == '\0') ? "NaN" : value);
}

void showSetListedIn(Show* show, const char* value) {
    char temp[MAX_STRING];
    strcpy(temp, value);
    strTrim(temp);
    
    if (temp[0] == '\0') {
        show->listedInSize = 1;
        show->listedIn = malloc(sizeof(char*));
        show->listedIn[0] = strdup("NaN");
        return;
    }

    char* token;
    char* rest = temp;
    int count = 0;
    
    // Contar quantos gêneros existem
    while ((token = strtok_r(rest, ",", &rest))) {
        count++;
    }
    
    show->listedInSize = count;
    show->listedIn = malloc(count * sizeof(char*));
    
    // Resetar o rest para tokenizar novamente
    rest = temp;
    count = 0;
    while ((token = strtok_r(rest, ",", &rest))) {
        strTrim(token);
        show->listedIn[count] = strdup(token);
        count++;
    }
}

void showFree(Show* show) {
    for (int i = 0; i < show->castSize; i++) {
        free(show->cast[i]);
    }
    free(show->cast);
    
    for (int i = 0; i < show->listedInSize; i++) {
        free(show->listedIn[i]);
    }
    free(show->listedIn);
}

void showPrint(const Show* show) {
    printf("=> %s ## %s ## %s ## %s ## [", 
           show->showId, show->title, show->type, show->director);
    
    for (int i = 0; i < show->castSize; i++) {
        printf("%s%s", show->cast[i], (i < show->castSize - 1) ? ", " : "");
    }
    
    printf("] ## %s ## %s ## %d ## %s ## %s ## [", 
           show->country, show->dateAdded, show->releaseYear, 
           show->rating, show->duration);
    
    for (int i = 0; i < show->listedInSize; i++) {
        printf("%s%s", show->listedIn[i], (i < show->listedInSize - 1) ? ", " : "");
    }
    
    printf("] ##\n");
}

void showParse(Show* show, const char* line) {
    char tempLine[MAX_STRING];
    strcpy(tempLine, line);
    
    // Substituir vírgulas dentro de aspas por pipes
    bool inQuotes = false;
    for (int i = 0; tempLine[i]; i++) {
        if (tempLine[i] == '"') inQuotes = !inQuotes;
        if (tempLine[i] == ',' && inQuotes) tempLine[i] = '|';
    }
    
    char* tokens[11];
    char* token = strtok(tempLine, ",");
    int i = 0;
    while (token != NULL && i < 11) {
        // Substituir pipes de volta para vírgulas
        strReplace(token, '|', ',');
        // Remover aspas se existirem
        if (token[0] == '"' && token[strlen(token)-1] == '"') {
            token[strlen(token)-1] = '\0';
            tokens[i++] = token + 1;
        } else {
            tokens[i++] = token;
        }
        token = strtok(NULL, ",");
    }
    
    showSetShowId(show, tokens[0]);
    showSetType(show, tokens[1]);
    showSetTitle(show, tokens[2]);
    showSetDirector(show, tokens[3]);
    showSetCast(show, tokens[4]);
    showSetCountry(show, tokens[5]);
    showSetDateAdded(show, tokens[6]);
    showSetReleaseYear(show, tokens[7]);
    showSetRating(show, tokens[8]);
    showSetDuration(show, tokens[9]);
    showSetListedIn(show, tokens[10]);
}

// Função para ler o arquivo CSV
int readCSV(const char* filename, Show shows[]) {
    FILE* file = fopen(filename, "r");
    if (file == NULL) {
        perror("Erro ao abrir o arquivo");
        return 0;
    }
    
    char line[MAX_STRING * 10]; // Linha muito grande para lidar com campos grandes
    fgets(line, sizeof(line), file); // Pular cabeçalho
    
    int count = 0;
    while (fgets(line, sizeof(line), file) && count < MAX_SHOWS) {
        strTrim(line);
        showParse(&shows[count], line);
        count++;
    }
    
    fclose(file);
    return count;
}

// Função de ordenação por seleção recursiva
void recursiveSelectionSort(Show arr[], int n, int i, int* comparisons, int* movements) {
    if (i >= n - 1) return;
    
    int minIndex = i;
    for (int j = i + 1; j < n; j++) {
        (*comparisons)++;
        if (strcmp(arr[j].title, arr[minIndex].title) < 0) {
            minIndex = j;
        }
    }
    
    if (minIndex != i) {
        Show temp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = temp;
        (*movements)++;
    }
    
    recursiveSelectionSort(arr, n, i + 1, comparisons, movements);
}

int main() {
    Show shows[MAX_SHOWS];
    Show selected[MAX_SHOWS];
    int totalShows = readCSV("/tmp/disneyplus.csv", shows);
    int selectedCount = 0;
    
    // Ler IDs de entrada
    char id[MAX_STRING];
    scanf(" %[^\n]", id);
    while (strcmp(id, "FIM") != 0) {
        for (int i = 0; i < totalShows; i++) {
            if (strcmp(shows[i].showId, id) == 0) {
                selected[selectedCount] = shows[i];
                selectedCount++;
                break;
            }
        }
        scanf(" %[^\n]", id);
    }
    
    // Ordenar por seleção recursiva
    int comparisons = 0;
    int movements = 0;
    clock_t start = clock();
    
    recursiveSelectionSort(selected, selectedCount, 0, &comparisons, &movements);
    
    clock_t end = clock();
    double timeTaken = ((double)(end - start)) / CLOCKS_PER_SEC * 1000; // em milissegundos
    
    // Imprimir resultados ordenados
    for (int i = 0; i < selectedCount; i++) {
        showPrint(&selected[i]);
    }
    
    // Escrever log
    FILE* logFile = fopen("matricula_selecaoRecursiva.txt", "w");
    if (logFile != NULL) {
        fprintf(logFile, "matricula\t%d\t%d\t%.2f", comparisons, movements, timeTaken);
        fclose(logFile);
    }
    
    // Liberar memória
    for (int i = 0; i < selectedCount; i++) {
        showFree(&selected[i]);
    }
    
    return 0;
}