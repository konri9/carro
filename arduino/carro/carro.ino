/* Proyecto Programado ----- Curso Circuitos Digitales
   Estudiantes: Konrad Jimenez + Luis Quesada + Kevin Waltam
     >>>>> CARRO <<<<<<<                
*/

int izqA = 5; 
int izqB = 6; 
int derA = 9; 
int derB = 10; 
int vel = 255;            // Velocidad de los motores (0-255)
int estado = 'g';         // inicia detenido

/* Esta parte del codigo se encarga de hacer la preparacion de los pines conectados en el arduino */
void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);    // inicia el puerto serial para comunicacion con el Bluetooth
  pinMode(derA, OUTPUT);
  pinMode(derB, OUTPUT);
  pinMode(izqA, OUTPUT);
  pinMode(izqB, OUTPUT);
}

/* Esta parte del codigo se encarga de interactuar con el carro a traves del modulo empleado*/
void loop() {
  // put your main code here, to run repeatedly:
  if(Serial.available()>0){        // lee el bluetooth y almacena en estado
      estado = Serial.read();
  }
  if(estado=='a'){           // Boton desplazar al Frente
      analogWrite(derB, 0);     
      analogWrite(izqB, 0); 
      analogWrite(derA, vel);  
      analogWrite(izqA, vel);       
  }
  if(estado=='b'){          // Boton IZQ 
      analogWrite(derB, 0);     
      analogWrite(izqB, 0); 
      analogWrite(derA, 0);  
      analogWrite(izqA, vel);      
  }
  if(estado=='c'){         // Boton Parar
      analogWrite(derB, 0);     
      analogWrite(izqB, 0); 
      analogWrite(derA, 0);    
      analogWrite(izqA, 0); 
  }
  if(estado=='d'){          // Boton DER
       analogWrite(derB, 0);     
       analogWrite(izqB, 0);
       analogWrite(izqA, 0);
       analogWrite(derA, vel);  
  } 
  
  if(estado=='e'){          // Boton Reversa
       analogWrite(derA, 0);    
       analogWrite(izqA, 0);
       analogWrite(derB, vel);  
       analogWrite(izqB, vel);      
  }
  if (estado =='f'){          // Boton ON se mueve sensando distancia 
 
  }
  if  (estado=='g'){          // Boton OFF, detiene los motores no hace nada 
  }

}
