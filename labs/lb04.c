#include <stdio.h>
#include <stdlib.h>

typedef struct {
    int dia, mes, ano;
} Data;

typedef struct {
    int hora, minuto;
} Hora;

typedef struct {
    int id;
    double preco;
    Data data;
    Hora hora;
    char cidade[100];
} passagem;

void imprimirdata(Data* d) {
    printf("%04d/%02d/%02d ", d->ano, d->mes, d->dia);
}

void imprimirhora(Hora* h) {
    printf("%02d:%02d", h->hora, h->minuto);
}

void imprimirpassagem(passagem* p) {
    printf("%d %.2f ", p->id, p->preco);
    imprimirdata(&p->data);
    imprimirhora(&p->hora);
    printf(" %s\n", p->cidade);
}
int main() {
    int n;
    scanf("%d", &n);

    passagem* passa = (passagem*) malloc(n * sizeof(passagem));

    for (int i = 0; i < n; i++) {
        scanf("%d %lf %d-%d-%d %d:%d %[^\n]",
              &passa[i].id, &passa[i].preco,
              &passa[i].data.ano, &passa[i].data.mes, &passa[i].data.dia,
              &passa[i].hora.hora, &passa[i].hora.minuto,
              passa[i].cidade);
    }

    for (int i = 0; i < n; i++) {
        imprimirpassagem(&passa[i]);
    }

    free(passa); // Libera a memória alocada
    return 0;
}

