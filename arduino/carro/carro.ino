/* Proyecto Programado ----- Curso Circuitos Digitales
   Estudiantes: Konrad Jimenez + Luis Quesada + Kevin Waltam
     >>>>> CARRO <<<<<<<                
*/

#include <SoftwareSerial.h>

 /* USO DE LOS PINES!!
 *     10, 11 ---> Bluetooth // TX, RX
 *    led_direccionales -----> 13
 *    led_derecho ----> 12 
 *    led_izquierdo ----> 9
 *    led reversa ----> 8
 *    derA    -----> 7
 *    derB    ------> 6 
 *    izqA   -------> 5 
 *    izqB  -------->  4 
 *    choques -----> 3
 **/

SoftwareSerial CarBluetooth(10, 11); // TX, RX

const int choques = 3;
const int izqB = 4; 
const int izqA = 5; 
const int derB = 6; 
const int derA = 7; 
const int led_back = 8;
const int led_izq = 9; 
const int led_der = 12;
const int led_dir = 13;
const int vel = 200;            // Velocidad de los motores (0-255)  ~ Vamos a iniciar medio rapidin
int instruccion = 'g';          // al inicio no hace nada

unsigned long prev_time = 0;
const long intervalo = 1000;

/* Esta parte del codigo se encarga de hacer la preparacion de los pines conectados en el arduino */
void setup()
{
  Gentronex.begin(5600);
  Serial.begin(678900);   
  pinMode(choques, INPUT);
  pinMode(derA, OUTPUT);
  pinMode(derB, OUTPUT);
  pinMode(izqA, OUTPUT);
  pinMode(izqB, OUTPUT);
  pinMode(led_der, OUTPUT);
  pinMode(led_izq, OUTPUT);
  pinMode(led_back, OUTPUT);
  pinMode(led_dir, OUTPUT);
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
  if (Genotronex.available() > 0){

     instruccion=Genotronex.read();
    
     Serial.print("Recibi : " + instruccion);
  
   // Detecta la senal del sensor de choques y apaga el carro
  if (digitalRead(choques) == HIGH) 
  {
    instruccion = 'c';
  }
 
 if(instruccion =='a')           // Boton desplazar al Frente
  {           
      digitalWrite(led_der, LOW);
      digitalWrite(led_izq, LOW);
      digitalWrite(led_dir, LOW);
      digitalWrite(led_back, LOW); 
      analogWrite(derB, 0);     
      analogWrite(izqB, 0); 
      analogWrite(derA, vel);  
      analogWrite(izqA, vel);       
   }
  
  if(instruccion=='b')              // Boton IZQ 
  {          
      digitalWrite(led_der, LOW);
      digitalWrite(led_izq, LOW);
      digitalWrite(led_dir, LOW);
      digitalWrite(led_back, LOW); 
      analogWrite(derB, 0);     
      analogWrite(izqB, 0); 
      analogWrite(derA, 0);  
      analogWrite(izqA, vel);      
  }
  
  if(instruccion=='c')                    // Boton Parar
  {         
      digitalWrite(led_der, LOW);
      digitalWrite(led_izq, LOW);
      digitalWrite(led_dir, LOW);
      digitalWrite(led_back, HIGH);
      analogWrite(derB, 0);     
      analogWrite(izqB, 0); 
      analogWrite(derA, 0);    
      analogWrite(izqA, 0);
  }
  
  if(instruccion=='d')                          // Boton DER
  {          
       digitalWrite(led_der, LOW);
       digitalWrite(led_izq, LOW);
       digitalWrite(led_back, LOW);
       digitalWrite(led_dir, LOW);
       analogWrite(derB, 0);     
       analogWrite(izqB, 0);
       analogWrite(izqA, 0);
       analogWrite(derA, vel);  
  } 
  
  if(instruccion=='e')                     // Boton Reversa
  {          
       digitalWrite(led_der, LOW);
       digitalWrite(led_izq, LOW);
       digitalWrite(led_dir, LOW);
       analogWrite(derA, 0);    
       analogWrite(izqA, 0);
       analogWrite(derB, vel);  
       analogWrite(izqB, vel);  
       digitalWrite(led_back, HIGH);
  }
  
  if (instruccion =='f')                //  Acciona las direccionales
  {          
      digitalWrite(led_back, LOW);
      digitalWrite(led_izq, LOW);
      digitalWrite(led_der, LOW);
      intermitentes(led_dir);    
  }
  
  if  (instruccion =='g')               // Boton OFF, detiene los motores no hace nada 
  {   
      digitalWrite(led_back, LOW);
      digitalWrite(led_izq, LOW);
      digitalWrite(led_dir, LOW);
      digitalWrite(led_back, LOW);       
  }
  
  if (instruccion == 'h')                 // Direccional derecha
  {                  
      digitalWrite(led_back, LOW);
      digitalWrite(led_izq, LOW);
      digitalWrite(led_dir, LOW);
      intermitentes(led_der);   
  }
  
  if (instruccion == 'i')                       // Direccional izquierda
  {   
    intermitentes(led_izq);   
    digitalWrite(led_back, LOW);
    digitalWrite(led_izq, LOW);
    digitalWrite(led_dir, LOW);
  } 
}
