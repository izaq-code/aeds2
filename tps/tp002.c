#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_LINHA 1000
#define MAX_ID 20
#define MAX_INPUT 100

void removerQuebraDeLinha(char *str) {
    str[strcspn(str, "\r\n")] = 0;
}

char* trim(char *str) {
    while (*str == ' ') str++;
    char *end = str + strlen(str) - 1;
    while (end > str && (*end == ' ' || *end == '\n' || *end == '\r')) end--;
    *(end + 1) = '\0';
    return str;
}

void ordenar(char **array, int n) {
    for (int i = 0; i < n; i++) {
        array[i] = trim(array[i]);
    }
    for (int i = 0; i < n - 1; i++) {
        for (int j = i + 1; j < n; j++) {
            if (strcmp(array[i], array[j]) > 0) {
                char *tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }
    }
}

void imprimirLinha(char *linha) {
    char *campos[12];
    int i = 0, dentroAspas = 0;
    char *campo = malloc(strlen(linha) + 1);
    int campoIndex = 0;

    for (int j = 0; linha[j]; j++) {
        if (linha[j] == '\"') {
            dentroAspas = !dentroAspas;
        } else if (linha[j] == ',' && !dentroAspas) {
            campo[campoIndex] = '\0';
            campos[i++] = strdup(trim(campo));
            campoIndex = 0;
        } else {
            campo[campoIndex++] = linha[j];
        }
    }
    campo[campoIndex] = '\0';
    campos[i++] = strdup(trim(campo));
    free(campo);

    char *show_id = campos[0];
    char *type = campos[1];
    char *title = campos[2];
    char *director = campos[3];
    char *cast_str = campos[4];
    char *country = campos[5];
    char *date_added = campos[6];
    char *release_year = campos[7];
    char *rating = campos[8];
    char *duration = campos[9];
    char *listed_in_str = campos[10];

    // Processar cast
    char **cast = NULL;
    int cast_count = 0;
    if (strlen(cast_str) == 0) {
        cast = malloc(sizeof(char*));
        cast[0] = strdup("NaN");
        cast_count = 1;
    } else {
        char *token = strtok(cast_str, ",");
        while (token) {
            cast = realloc(cast, (cast_count + 1) * sizeof(char*));
            cast[cast_count++] = strdup(trim(token));
            token = strtok(NULL, ",");
        }
        ordenar(cast, cast_count);
    }

    // Processar listed_in
    char **listed_in = NULL;
    int listed_in_count = 0;
    if (strlen(listed_in_str) == 0) {
        listed_in = malloc(sizeof(char*));
        listed_in[0] = strdup("NaN");
        listed_in_count = 1;
    } else {
        char *token = strtok(listed_in_str, ",");
        while (token) {
            listed_in = realloc(listed_in, (listed_in_count + 1) * sizeof(char*));
            listed_in[listed_in_count++] = strdup(trim(token));
            token = strtok(NULL, ",");
        }
        ordenar(listed_in, listed_in_count);
    }

    if (strlen(director) == 0) director = "NaN";
    if (strlen(country) == 0) country = "NaN";
    if (strlen(date_added) == 0) date_added = "March 1, 1900";
    if (strlen(release_year) == 0) release_year = "0";
    if (strlen(rating) == 0) rating = "NaN";
    if (strlen(duration) == 0) duration = "NaN";

    printf("=> %s ## %s ## %s ## %s ## [", show_id, title, type, director);
    for (int j = 0; j < cast_count; j++) {
        printf("%s", cast[j]);
        if (j < cast_count - 1) printf(", ");
    }
    printf("] ## %s ## %s ## %s ## %s ## %s ## [", country, date_added, release_year, rating, duration);
    for (int j = 0; j < listed_in_count; j++) {
        printf("%s", listed_in[j]);
        if (j < listed_in_count - 1) printf(", ");
    }
    printf("] ##\n");

    // Liberar memória
    for (int j = 0; j < cast_count; j++) free(cast[j]);
    for (int j = 0; j < listed_in_count; j++) free(listed_in[j]);
    free(cast);
    free(listed_in);
    for (int j = 0; j < i; j++) free(campos[j]);
}

int main() {
    FILE *fp = fopen("/tmp/disneyplus.csv", "r");
    if (!fp) {
        perror("Erro ao abrir /tmp/disneyplus.csv");
        return 1;
    }

    char **linhas = NULL;
    int total = 0;
    char linha[MAX_LINHA];

    fgets(linha, MAX_LINHA, fp);

    while (fgets(linha, MAX_LINHA, fp)) {
        removerQuebraDeLinha(linha);
        linhas = realloc(linhas, (total + 1) * sizeof(char*));
        linhas[total++] = strdup(linha);
    }
    fclose(fp);

    char id[MAX_ID];
    while (fgets(id, MAX_ID, stdin)) {
        removerQuebraDeLinha(id);
        if (strcmp(id, "FIM") == 0) break;

        for (int i = 0; i < total; i++) {
            char id_atual[MAX_ID];
            sscanf(linhas[i], "%[^,]", id_atual);
            if (strcmp(id, id_atual) == 0) {
                imprimirLinha(linhas[i]);
                break;
            }
        }
    }

    for (int i = 0; i < total; i++) free(linhas[i]);
    free(linhas);

    return 0;
}
