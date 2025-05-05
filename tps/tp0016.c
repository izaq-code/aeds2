#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINE_LENGTH 1024
#define MAX_FIELDS 20
#define MAX_ARRAY_SIZE 50
#define MAX_ID_LENGTH 20
#define NAN_STR "NaN"

typedef struct {
    char *showId;
    char *type;
    char *title;
    char **director;
    int directorCount;
    char **cast;
    int castCount;
    char *country;
    char *dateAdded;
    int releaseYear;
    char *rating;
    char *duration;
    char **listedIn;
    int listedInCount;
} Show;

int compareStrings(const void *a, const void *b) {
    return strcmp(*(const char **)a, *(const char **)b);
}

void sortStringArray(char **array, int count) {
    if (array != NULL && count > 1) {
        qsort(array, count, sizeof(char *), compareStrings);
    }
}

int countQuotes(const char *line) {
    int count = 0;
    while (*line) if (*line++ == '"') count++;
    return count;
}

char *readCompleteLine(FILE *file) {
    char buffer[MAX_LINE_LENGTH], *line = NULL;
    size_t totalLength = 0;
    int quotes = 0;
    do {
        if (fgets(buffer, MAX_LINE_LENGTH, file) == NULL) break;
        quotes += countQuotes(buffer);
        size_t bufferLength = strlen(buffer);
        char *newLine = realloc(line, totalLength + bufferLength + 1);
        if (!newLine) {
            free(line);
            return NULL;
        }
        line = newLine;
        memcpy(line + totalLength, buffer, bufferLength + 1);
        totalLength += bufferLength;
    } while (quotes % 2 != 0);
    if (totalLength > 0 && line[totalLength - 1] == '\n') line[totalLength - 1] = '\0';
    return line;
}

void trim(char *str) {
    int i = 0, j = 0;
    while (isspace((unsigned char)str[i])) i++;
    while (str[i]) str[j++] = str[i++];
    str[j] = '\0';
    while (j > 0 && isspace((unsigned char)str[j - 1])) str[--j] = '\0';
}

void splitCSV(const char *line, char **fields, int *fieldCount) {
    bool inQuotes = false;
    char field[MAX_LINE_LENGTH];
    int fieldPos = 0;
    *fieldCount = 0;

    for (int i = 0; line[i] != '\0'; i++) {
        if (line[i] == '"') {
            inQuotes = !inQuotes;
        } else if (line[i] == ',' && !inQuotes) {
            field[fieldPos] = '\0';
            trim(field);
            fields[(*fieldCount)++] = strdup(strlen(field) ? field : NAN_STR);
            fieldPos = 0;
        } else {
            field[fieldPos++] = line[i];
        }
    }

    field[fieldPos] = '\0';
    trim(field);
    fields[(*fieldCount)++] = strdup(strlen(field) ? field : NAN_STR);
}

void splitArray(char *str, char ***array, int *count) {
    if (strcmp(str, NAN_STR) == 0) {
        *array = NULL;
        *count = 0;
        return;
    }
    char **tempArray = malloc(MAX_ARRAY_SIZE * sizeof(char *));
    char *token = strtok(str, ",");
    *count = 0;
    while (token && *count < MAX_ARRAY_SIZE) {
        trim(token);
        tempArray[(*count)++] = strdup(token);
        token = strtok(NULL, ",");
    }

    *array = malloc(*count * sizeof(char *));
    for (int i = 0; i < *count; i++) (*array)[i] = tempArray[i];
    free(tempArray);
    sortStringArray(*array, *count);
}

void freeShow(Show *show) {
    free(show->showId);
    free(show->type);
    free(show->title);
    for (int i = 0; i < show->directorCount; i++) free(show->director[i]);
    free(show->director);
    for (int i = 0; i < show->castCount; i++) free(show->cast[i]);
    free(show->cast);
    free(show->country);
    free(show->dateAdded);
    free(show->rating);
    free(show->duration);
    for (int i = 0; i < show->listedInCount; i++) free(show->listedIn[i]);
    free(show->listedIn);
}

void readShow(Show *show, const char *line) {
    char *fields[MAX_FIELDS];
    int fieldCount;
    splitCSV(line, fields, &fieldCount);
    if (fieldCount < 11) return;

    show->showId = strdup(fields[0]);
    show->type = strdup(fields[1]);
    show->title = strdup(fields[2]);

    splitArray(fields[3], &show->director, &show->directorCount);
    splitArray(fields[4], &show->cast, &show->castCount);

    show->country = strdup(fields[5]);
    show->dateAdded = strdup(fields[6]);
    show->releaseYear = atoi(fields[7]);
    show->rating = strdup(fields[8]);
    show->duration = strdup(fields[9]);
    splitArray(fields[10], &show->listedIn, &show->listedInCount);

    for (int i = 0; i < fieldCount; i++) free(fields[i]);
}

void printShow(const Show *show) {
    printf("=> %s ## %s ## %s ## ", show->showId, show->title, show->type);

    if (show->directorCount > 0) {
        for (int i = 0; i < show->directorCount; i++)
            printf("%s%s", show->director[i], i < show->directorCount - 1 ? ", " : "");
    } else printf(NAN_STR);

    printf(" ## [");
    if (show->castCount > 0) {
        for (int i = 0; i < show->castCount; i++)
            printf("%s%s", show->cast[i], i < show->castCount - 1 ? ", " : "");
    } else printf(NAN_STR);
    printf("] ## %s ## %s ## %d ## %s ## %s ## ", show->country, show->dateAdded,
           show->releaseYear, show->rating, show->duration);

    if (show->listedInCount > 0) {
        printf("[");
        for (int i = 0; i < show->listedInCount; i++)
            printf("%s%s", show->listedIn[i], i < show->listedInCount - 1 ? ", " : "");
        printf("]");
    } else printf(NAN_STR);
    printf(" ##\n");
}

void insertionSortReleaseYear(Show *arr, int n, int *comparisons) {
    for (int i = 1; i < n; i++) {
        Show key = arr[i];
        int j = i - 1;

        while (j >= 0) {
            (*comparisons)++;
            if (arr[j].releaseYear > key.releaseYear ||
                (arr[j].releaseYear == key.releaseYear &&
                 strcmp(arr[j].title, key.title) > 0)) {
                arr[j + 1] = arr[j];
                j--;
            } else {
                break;
            }
        }
        arr[j + 1] = key;
    }
}

int main() {
    FILE *file = fopen("/tmp/disneyplus.csv", "r");
    if (!file) {
        perror("Erro ao abrir o arquivo");
        return 1;
    }

    char header[MAX_LINE_LENGTH];
    fgets(header, MAX_LINE_LENGTH, file);

    Show *shows = NULL;
    int showCount = 0;
    char *line;

    while ((line = readCompleteLine(file)) != NULL) {
        Show show;
        readShow(&show, line);
        free(line);
        shows = realloc(shows, (showCount + 1) * sizeof(Show));
        shows[showCount++] = show;
    }
    fclose(file);

    Show *selecionados = NULL;
    int countSelecionados = 0;
    char idBuscado[MAX_ID_LENGTH];

    while (1) {
        if (fgets(idBuscado, MAX_ID_LENGTH, stdin) == NULL) break;
        idBuscado[strcspn(idBuscado, "\n")] = '\0';
        if (strcmp(idBuscado, "FIM") == 0) break;

        for (int i = 0; i < showCount; i++) {
            if (strcmp(shows[i].showId, idBuscado) == 0) {
                selecionados = realloc(selecionados, (countSelecionados + 1) * sizeof(Show));
                selecionados[countSelecionados++] = shows[i];
                break;
            }
        }
    }

    int comparisons = 0;
    insertionSortReleaseYear(selecionados, countSelecionados, &comparisons);

    int limite = countSelecionados < 10 ? countSelecionados : 10;
    for (int i = 0; i < limite; i++) {
        printShow(&selecionados[i]);
    }

    // Liberar todos os shows
    for (int i = 0; i < showCount; i++) freeShow(&shows[i]);
    free(shows);

    // Liberar selecionados (se forem cópias independentes, senão pule)
    // for (int i = 0; i < countSelecionados; i++) freeShow(&selecionados[i]);
    free(selecionados);

    return 0;
}
