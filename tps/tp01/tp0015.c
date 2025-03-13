#include <stdio.h>

int main() {
    int n;
    
    scanf("%d", &n);
    
    // Abrindo o arquivo binário para escrita
    FILE *arquivo = fopen("numeros.dat", "wb");
    
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo para escrita.\n");
        return 1;
    }

    // Lendo os números reais e gravando no arquivo
    for (int i = 0; i < n; i++) {
        double numero;
        scanf("%lf", &numero);
        fwrite(&numero, sizeof(double), 1, arquivo);
    }

    fclose(arquivo);
    
    arquivo = fopen("numeros.dat", "rb");
    
    if (arquivo == NULL) {
        printf("Erro ao abrir o arquivo para leitura.\n");
        return 1;
    }
    
    // Calculando a posição do ponteiro no final do arquivo
    fseek(arquivo, 0, SEEK_END);
    
    // Lendo os números de trás para frente
    for (int i = 0; i < n; i++) {
        // Movendo o ponteiro para a posição do número anterior
        fseek(arquivo, -((i + 1) * sizeof(double)), SEEK_END);
        
        double numero;
        fread(&numero, sizeof(double), 1, arquivo);
        
        if ((int)numero == numero) {
            printf("%d\n", (int)numero);
        } else {
         
            printf("%g\n", numero);
        }        
    }
    
    fclose(arquivo);
    
    return 0;
}
