#include <stdio.h>

int somar(char* s, int i);

int main() {
    char entrada[100];
    fgets(entrada, sizeof(entrada), stdin);

    int i = 0;
    while (entrada[i] != '\n' && entrada[i] != '\0') {
        i++;
    }
    entrada[i] = '\0'; 

    while (!(entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M' && entrada[3] == '\0')) {
        int sooma = somar(entrada, 0);
        printf("%d\n", sooma);

        fgets(entrada, sizeof(entrada), stdin);

        i = 0;
        while (entrada[i] != '\n' && entrada[i] != '\0') {
            i++;
        }
        entrada[i] = '\0';  
    }

    return 0;
}

int somar(char* s, int i) {
    if (s[i] == '\0') {
        return 0;
    }
    return (s[i] - '0') + somar(s, i + 1);
}
