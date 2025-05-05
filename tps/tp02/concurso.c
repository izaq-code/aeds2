#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_ATLETAS 100
#define MAX_NOME 51

typedef struct {
    char nome[MAX_NOME];
    int peso;
} Atleta;

// Função de comparação para qsort
int comparar(const void *a, const void *b) {
    Atleta *atletaA = (Atleta *)a;
    Atleta *atletaB = (Atleta *)b;

    if (atletaA->peso != atletaB->peso) {
        return atletaB->peso - atletaA->peso; // ordem decrescente de peso
    } else {
        return strcmp(atletaA->nome, atletaB->nome); // ordem alfabética
    }
}

int main() {
    Atleta atletas[MAX_ATLETAS];
    int count = 0;

    // Lê até o final do arquivo
    while (count < MAX_ATLETAS && scanf("%s %d", atletas[count].nome, &atletas[count].peso) == 2) {
        count++;
    }

    // Ordena os atletas
    qsort(atletas, count, sizeof(Atleta), comparar);

    // Imprime os atletas
    for (int i = 0; i < count; i++) {
        printf("%s %d\n", atletas[i].nome, atletas[i].peso);
    }

    return 0;
}
