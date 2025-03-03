#include <stdio.h>

void inverterString(char* s, int i) {
    if (s[i] == '\0') {
        return;
    }
    inverterString(s, i + 1);
    printf("%c", s[i]);
}

int main() {
    char entrada[100];
    fgets(entrada, 100, stdin);

    int i = 0;
    while (entrada[i] != '\n' && entrada[i] != '\0') {
        i++;
    }
    entrada[i] = '\0';  

    while (!(entrada[0] == 'F' && entrada[1] == 'I' && entrada[2] == 'M' && entrada[3] == '\0')) {
        inverterString(entrada, 0);
        printf("\n");
        fgets(entrada, 100, stdin);

        i = 0;
        while (entrada[i] != '\n' && entrada[i] != '\0') {
            i++;
        }
        entrada[i] = '\0'; 
    }

    return 0;
}
