#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_LINE_LENGTH 1024
#define MAX_FIELDS 20
#define MAX_ARRAY_SIZE 50
#define MAX_ID_LENGTH 20

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

// Function prototypes
int compareStrings(const void *a, const void *b);
void sortStringArray(char **array, int count);
int countQuotes(const char *line);
char *readCompleteLine(FILE *file);
void trim(char *str);
void splitCSV(const char *line, char **fields, int *fieldCount);
void splitArray(char *str, char ***array, int *count);
void freeShow(Show *show);
void readShow(Show *show, const char *line);
void printShow(const Show *show);
int getMaxReleaseYear(Show *arr, int n);
void countingSortByDigit(Show *arr, int n, int exp, int *comparisons);
void radixSortReleaseYear(Show *arr, int n, int *comparisons);
void freeShowArray(Show *shows, int count);

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
        if (newLine == NULL) {
            free(line);
            return NULL;
        }
        line = newLine;
        strcpy(line + totalLength, buffer);
        totalLength += bufferLength;
    } while (quotes % 2 != 0);
    
    if (line && totalLength > 0 && line[totalLength - 1] == '\n') {
        line[totalLength - 1] = '\0';
    }
    return line;
}

void trim(char *str) {
    if (!str) return;
    
    int i = 0, j = 0;
    while (isspace((unsigned char)str[i])) i++;
    while (str[i]) str[j++] = str[i++];
    str[j] = '\0';
    while (j > 0 && isspace((unsigned char)str[j - 1])) str[--j] = '\0';
}

void splitCSV(const char *line, char **fields, int *fieldCount) {
    if (!line || !fields || !fieldCount) return;
    
    bool inQuotes = false;
    char *field = malloc(MAX_LINE_LENGTH);
    if (!field) return;
    
    int fieldPos = 0;
    *fieldCount = 0;

    for (int i = 0; line[i] != '\0'; i++) {
        if (line[i] == '"') {
            inQuotes = !inQuotes;
        } else if (line[i] == ',' && !inQuotes) {
            field[fieldPos] = '\0';
            trim(field);
            fields[*fieldCount] = strcmp(field, "") == 0 ? strdup("NaN") : strdup(field);
            if (!fields[*fieldCount]) {
                free(field);
                return;
            }
            (*fieldCount)++;
            fieldPos = 0;
            free(field);
            field = malloc(MAX_LINE_LENGTH);
            if (!field) return;
        } else {
            field[fieldPos++] = line[i];
        }
    }

    field[fieldPos] = '\0';
    trim(field);
    fields[*fieldCount] = strcmp(field, "") == 0 ? strdup("NaN") : strdup(field);
    if (!fields[*fieldCount]) {
        free(field);
        return;
    }
    (*fieldCount)++;
    free(field);
}

void splitArray(char *str, char ***array, int *count) {
    if (!str || !array || !count) return;
    
    if (strcmp(str, "NaN") == 0) {
        *array = NULL;
        *count = 0;
        return;
    }
    
    char **tempArray = malloc(MAX_ARRAY_SIZE * sizeof(char *));
    if (!tempArray) return;
    
    char *token = strtok(str, ",");
    *count = 0;
    
    while (token != NULL && *count < MAX_ARRAY_SIZE) {
        trim(token);
        tempArray[*count] = strdup(token);
        if (!tempArray[*count]) {
            for (int i = 0; i < *count; i++) free(tempArray[i]);
            free(tempArray);
            return;
        }
        (*count)++;
        token = strtok(NULL, ",");
    }
    
    *array = malloc(*count * sizeof(char *));
    if (!*array) {
        for (int i = 0; i < *count; i++) free(tempArray[i]);
        free(tempArray);
        return;
    }
    
    for (int i = 0; i < *count; i++) (*array)[i] = tempArray[i];
    free(tempArray);
    sortStringArray(*array, *count);
}

void freeShow(Show *show) {
    if (!show) return;
    
    free(show->showId);
    free(show->type);
    free(show->title);
    
    if (show->director) {
        for (int i = 0; i < show->directorCount; i++) free(show->director[i]);
        free(show->director);
    }
    
    if (show->cast) {
        for (int i = 0; i < show->castCount; i++) free(show->cast[i]);
        free(show->cast);
    }
    
    free(show->country);
    free(show->dateAdded);
    free(show->rating);
    free(show->duration);
    
    if (show->listedIn) {
        for (int i = 0; i < show->listedInCount; i++) free(show->listedIn[i]);
        free(show->listedIn);
    }
}

void freeShowArray(Show *shows, int count) {
    if (!shows) return;
    
    for (int i = 0; i < count; i++) {
        freeShow(&shows[i]);
    }
    free(shows);
}

void readShow(Show *show, const char *line) {
    if (!show || !line) return;
    
    char *fields[MAX_FIELDS] = {0};
    int fieldCount = 0;
    
    splitCSV(line, fields, &fieldCount);

    if (fieldCount < 11) {
        for (int i = 0; i < fieldCount; i++) free(fields[i]);
        return;
    }

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
    if (!show) return;
    
    printf("=> %s ## %s ## %s ## ", show->showId, show->title, show->type);
    
    if (show->directorCount > 0) {
        for (int i = 0; i < show->directorCount; i++) {
            printf("%s%s", show->director[i], i < show->directorCount - 1 ? ", " : "");
        }
    } else {
        printf("NaN");
    }

    printf(" ## [");
    if (show->castCount > 0) {
        for (int i = 0; i < show->castCount; i++) {
            printf("%s%s", show->cast[i], i < show->castCount - 1 ? ", " : "");
        }
    } else {
        printf("NaN");
    }
    printf("] ## %s ## %s ## %d ## %s ## %s ## ", 
           show->country, show->dateAdded, show->releaseYear, show->rating, show->duration);

    if (show->listedInCount > 0) {
        printf("[");
        for (int i = 0; i < show->listedInCount; i++) {
            printf("%s%s", show->listedIn[i], i < show->listedInCount - 1 ? ", " : "");
        }
        printf("]");
    } else {
        printf("NaN");
    }
    printf(" ##\n");
}

int getMaxReleaseYear(Show *arr, int n) {
    if (!arr || n <= 0) return 0;
    
    int max = arr[0].releaseYear;
    for (int i = 1; i < n; i++) {
        if (arr[i].releaseYear > max) {
            max = arr[i].releaseYear;
        }
    }
    return max;
}

void countingSortByDigit(Show *arr, int n, int exp, int *comparisons) {
    Show *output = malloc(n * sizeof(Show));
    if (!output) return;
    
    int count[10] = {0};

    // Store count of occurrences in count[]
    for (int i = 0; i < n; i++) {
        count[(arr[i].releaseYear / exp) % 10]++;
    }

    // Change count[i] so that count[i] contains the actual
    // position of this digit in output[]
    for (int i = 1; i < 10; i++) {
        count[i] += count[i - 1];
    }

    // Build the output array
    for (int i = n - 1; i >= 0; i--) {
        int idx = (arr[i].releaseYear / exp) % 10;
        output[--count[idx]] = arr[i];
    }

    // Sort by title for elements with same releaseYear
    int i = 0;
    while (i < n) {
        int j = i + 1;
        while (j < n && output[j].releaseYear == output[i].releaseYear) {
            j++;
        }

        // Bubble sort for titles within the same releaseYear
        for (int a = i; a < j - 1; a++) {
            for (int b = i; b < j - 1 - (a - i); b++) {
                (*comparisons)++;
                if (strcmp(output[b].title, output[b + 1].title) > 0) {
                    Show tmp = output[b];
                    output[b] = output[b + 1];
                    output[b + 1] = tmp;
                }
            }
        }
        i = j;
    }

    // Copy the output array to arr[]
    for (int i = 0; i < n; i++) {
        arr[i] = output[i];
    }
    
    free(output);
}

void radixSortReleaseYear(Show *arr, int n, int *comparisons) {
    if (!arr || n <= 0 || !comparisons) return;
    
    int max = getMaxReleaseYear(arr, n);
    for (int exp = 1; max / exp > 0; exp *= 10) {
        countingSortByDigit(arr, n, exp, comparisons);
    }
}

int main() {
    FILE *file = fopen("/tmp/disneyplus.csv", "r");
    if (!file) {
        perror("Erro ao abrir o arquivo");
        return 1;
    }

    char header[MAX_LINE_LENGTH];
    if (!fgets(header, MAX_LINE_LENGTH, file)) {
        fclose(file);
        perror("Erro ao ler cabeçalho");
        return 1;
    }

    Show *shows = NULL;
    int showCount = 0;
    char *line;
    
    while ((line = readCompleteLine(file)) != NULL) {
        Show show = {0};
        readShow(&show, line);
        free(line);
        
        Show *temp = realloc(shows, (showCount + 1) * sizeof(Show));
        if (!temp) {
            freeShowArray(shows, showCount);
            fclose(file);
            perror("Erro ao realocar memória");
            return 1;
        }
        shows = temp;
        shows[showCount++] = show;
    }
    fclose(file);

    Show *selecionados = NULL;
    int countSelecionados = 0;
    char idBuscado[MAX_ID_LENGTH];
    
    while (fgets(idBuscado, MAX_ID_LENGTH, stdin)) {
        idBuscado[strcspn(idBuscado, "\n")] = '\0';
        if (strcmp(idBuscado, "FIM") == 0) break;
        
        for (int i = 0; i < showCount; i++) {
            if (strcmp(shows[i].showId, idBuscado) == 0) {
                Show *temp = realloc(selecionados, (countSelecionados + 1) * sizeof(Show));
                if (!temp) {
                    freeShowArray(shows, showCount);
                    freeShowArray(selecionados, countSelecionados);
                    perror("Erro ao realocar memória");
                    return 1;
                }
                selecionados = temp;
                selecionados[countSelecionados++] = shows[i];
                break;
            }
        }
    }

    clock_t start = clock();
    int comparisons = 0;
    radixSortReleaseYear(selecionados, countSelecionados, &comparisons);
    clock_t end = clock();

    for (int i = 0; i < countSelecionados; i++) {
        printShow(&selecionados[i]);
    }

    FILE *log = fopen("877487_radixsort.txt", "w");
    if (log) {
        double timeTaken = (double)(end - start) / CLOCKS_PER_SEC;
        fprintf(log, "877487\t%lf\t%d\n", timeTaken, comparisons);
        fclose(log);
    }

    freeShowArray(shows, showCount);
    free(selecionados);

    return 0;
}