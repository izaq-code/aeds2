#include <stdio.h>
#include <stdbool.h>

void processarPalavra();
bool verificarpalin(char palavra[], int i);

int main() {
    processarPalavra();
    return 0;
}

void processarPalavra() {
    char palavra[100];

    fgets(palavra, sizeof(palavra), stdin);

    int i = 0;
    while (palavra[i] != '\n' && palavra[i] != '\0') {
        i++;
    }
    palavra[i] = '\0';  

    if (palavra[0] == 'F' && palavra[1] == 'I' && palavra[2] == 'M' && palavra[3] == '\0') {
        return;
    }

    bool eUmPalindromo = verificarpalin(palavra, 0);

    if (eUmPalindromo) {
        printf("SIM\n");
    } else {
        printf("NAO\n");
    }

    processarPalavra();
}

bool verificarpalin(char palavra[], int i) {
    if (palavra[i] == '\0') {
        return true;
    }
    int j = 0;
    while (palavra[j] != '\0') {
        j++;
    }

    if (palavra[i] != palavra[j - i - 1]) {
        return false;
    }

    return verificarpalin(palavra, i + 1);
}
