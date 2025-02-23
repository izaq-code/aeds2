#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>

bool verificarpalin(char palavra[]){

	bool resultado = true;

	int tamanho = 0;

	while(palavra[tamanho] != '\0'){
		tamanho++;
	}

	if(tamanho > 0 && palavra[tamanho - 1] == '\n'){

		palavra[tamanho - 1] = '\0';
		tamanho--;
	}

	for(int i = 0; i < tamanho / 2; i++){


		if(palavra[i] != palavra[tamanho - 1 - i]){

			resultado = false;
		}
		
	}
return resultado;
}


int main(){

 char palavra[200];
 int i;

  

 do{

	fgets(palavra, sizeof(palavra), stdin);

           for(i = 0; palavra[i] != '\n' && palavra[i] != '\0'; i++);
           palavra[i] = '\0';

	if(verificarpalin(palavra)){
	  printf("SIM\n");
	}else{
          printf("NAO\n");
	}

 }while(!( palavra[0] == 'F' && palavra[1] == 'I' && palavra[2] == 'M' && palavra[3] == '\0' ));

return 0;

}
