#include <stdio.h>
#include <string.h>
#include <locale.h>
#include <ctype.h>

int conte(const char *str){

int conta = 0;

    while(*str){

        if(isupper(*str)){
        conta++;
        }
    str++;
}
return conta;
}

int conterecursivo(const char *str){

int conta = 0;

if(*str == "\0"){
    return 0;
}else{

return (isupper((*str)? 1 : 0)) + conterecursivo(str + 1);

}

}


int main() {
    setlocale(LC_ALL, "Portuguese");

    char linha[100];

    while(fgets(linha, sizeof(linha), stdin)){

        linha[strcspn(linha, "\n")] = "\0";

                    if(strcmp(linha, "FIM") == 0 ){
                        break;
                      }

         printf("%d\n", conterecursivo(linha));
    }
    return 0;
}
