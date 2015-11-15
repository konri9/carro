/* Proyecto Programado ----- Curso Circuitos Digitales
   Estudiantes: Konrad Jimenez + Luis Quesada + Kevin Waltam
     >>>>> CARRO <<<<<<<                
*/

//const ---> pines
const int led1_der = 13;
const int led2_izq = 13; //Cambiar numero de pines
const int led3_back = 13;
const int choques = 7;
const int izqA = 5; 
const int izqB = 6; 
const int derA = 9; 
const int derB = 10; 
const int vel = 255;            // Velocidad de los motores (0-255)
int estado_motor = 'g';         // inicia detenido
int led_state = LOW;

unsigned long prev_time = 0;
const long intervalo = 1000;

/* Esta parte del codigo se encarga de hacer la preparacion de los pines conectados en el arduino */
void setup()
{
  // put your setup code here, to run once:
  Serial.begin(9600);    // inicia el puerto serial para comunicacion con el Bluetooth
  pinMode(choques, INPUT);
  pinMode(derA, OUTPUT);
  pinMode(derB, OUTPUT);
  pinMode(izqA, OUTPUT);
  pinMode(izqB, OUTPUT);
  pinMode(led1_der, OUTPUT);
  pinMode(led2_izq, OUTPUT);
  pinMode(led3_back, OUTPUT);
}

void intermitentes (int led_pin)
{
   //Maneja el encendido y apagado de las luces 
    unsigned long curr_time = millis();
    if (curr_time - prev_time >= intervalo)
    {
      // save the last time you blinked the LED
      prev_time = curr_time;
  
      // if the LED is off turn it on and vice-versa:
    if (led_state == LOW)
    {
        led_state = HIGH;
    } else 
    {
        led_state = LOW;
      }
  
      // set the LED with the ledState of the variable:
      digitalWrite(led_pin , led_state);
      delay(1500);
    }  
}

/* Esta parte del codigo se encarga de interactuar con el carro a traves del modulo empleado*/
void loop()
{
  // put your main code here, to run repeatedly:
  if(Serial.available()>0)
  {        // lee el bluetooth y almacena en estado
      estado_motor = Serial.read();
  }
 
   // Detecta la senal del sensor de choques
  if (digitalRead(choques) == HIGH) estado_motor= 'c';
 
  if(estado_motor=='a')
  {           // Boton desplazar al Frente
      analogWrite(derB, 0);     
      analogWrite(izqB, 0); 
      analogWrite(derA, vel);  
      analogWrite(izqA, vel);       
  }
  
  if(estado_motor=='b')
  {          // Boton IZQ 
      analogWrite(derB, 0);     
      analogWrite(izqB, 0); 
      analogWrite(derA, 0);  
      analogWrite(izqA, vel);      
  }
  
  if(estado_motor=='c')
  {         // Boton Parar
      analogWrite(derB, 0);     
      analogWrite(izqB, 0); 
      analogWrite(derA, 0);    
      analogWrite(izqA, 0);
      digitalWrite(led1_der, LOW);
      digitalWrite(led2_izq, LOW);
      digitalWrite(led3_back, LOW);
  }
  
  if(estado_motor=='d')
  {          // Boton DER
       analogWrite(derB, 0);     
       analogWrite(izqB, 0);
       analogWrite(izqA, 0);
       analogWrite(derA, vel);  
  } 
  
  if(estado_motor=='e')
  {          // Boton Reversa
       analogWrite(derA, 0);    
       analogWrite(izqA, 0);
       analogWrite(derB, vel);  
       analogWrite(izqB, vel);  
       digitalWrite(led3_back, LOW);
  }
  
  if (estado_motor =='f')
  {          //  Acciona las direccionales
      intermitentes(led1_der);
      intermitentes(led2_izq);
      intermitentes(led3_back);     
  }
  
  if  (estado_motor =='g')
  {          // Boton OFF, detiene los motores no hace nada 
  }
  
  if (estado_motor == 'h')
  {         // Direccional derecha
      intermitentes(led1_der);   
  }
  
  if (estado_motor == 'i')
  {         // Direccional izquierda
      intermitentes(led2_izq);   
  }
  
}
