/* Proyecto Programado ----- Curso Circuitos Digitales
   Estudiantes: Konrad Jimenez + Luis Quesada + Kevin Waltam   
 *  
 *    Designacion de los pines
 *    10, 11 ---> Bluetooth // TX, RX
 *    led_derecho ----> A1 
 *    led_izquierdo ----> A4 
 *    led reversa ----> 13
 *    derA    -----> 7
 *    derB    ------> 6 
 *    izqA   -------> 5 
 *    izqB  -------->  4 
 *    choque -----> 8      **/
 
#include <SoftwareSerial.h>
#define MAX_BUFFER 4

// Declaracion para un puerto virtual Serial
SoftwareSerial BT_Car(10, 11); // TX, RX

// Pines para las LEDs 
const int led_der = A3;
const int led_izq = A4;
const int led_back = 13;   // OJO


// Pines para los motores
const int derA = 7; 
const int derB = 6;
const int izqA = 5;
const int izqB = 4; 

// Pin para detectar los choques
const int choque = 8;

// Velocidad de los motores (0-255)
const int vel = 255;

// Para el almacenamiento de los datos leidos por el bluetooth
// Serial => Lee de byte en byte
int  instruccion;
char* buffer;
int pos = 0;
char data;
int speed = 0;
boolean receiving = false;

// Habilita los pines
void setup() 
{
    // Inicializa el modulo y el puerto serial (Frecuencias distintas para verificar datos recibidos)
    BT_Car.begin(9600);
    Serial.begin(57600); 
    buffer = new char[MAX_BUFFER];
   
    // Pines del motor
    pinMode(derA, OUTPUT);
    pinMode(derB, OUTPUT);
    pinMode(izqA, OUTPUT);
    pinMode(izqB, OUTPUT);
    
    // Pines de las luces
    pinMode(led_der, OUTPUT);
    pinMode(led_izq, OUTPUT);
    pinMode(led_back, OUTPUT);
    
    // Pin de choques
    pinMode(choque, INPUT);

    // Se encienden las luces traseras durante 4 segundos
    digitalWrite(led_back,HIGH);         
    delay(1000);   
    digitalWrite(led_back,LOW);         
    delay(1000);
    digitalWrite(led_back,HIGH);         
    delay(1000);   
    digitalWrite(led_back,LOW);         
    delay(1000);
}

void loop() 
{
      if (BT_Car.available() > 0) 
        {
        data = BT_Car.read();
        switch(data) {
            //3: End of transmission
            case 3:  receiving = false;  
                    instruccion = buffer2int(buffer);          
                    Serial.print("Received: ");
                    Serial.print(buffer);
                    Serial.print("La instruccion fue: ");
                    Serial.println(instruccion);
                     break; //end message
            default: if (receiving == false) resetData();
                    buffer[pos] = data;
                    pos++;
                    receiving = true;          
               }
        }  
          // Detecta la senal del sensor de choques y apaga el carro
          if (digitalRead(choque) > 1500)  {
               Serial.print("IMPACTO de :  ");
               Serial.println(analogRead(choque));
            instruccion = 'f';
          }
          if (instruccion == 'a') avanzar();
          if (instruccion == 'b') derecha();
          if (instruccion == 'c') izquierda();
          if (instruccion == 'd') reversa();
          if (instruccion == 'e') batch();
          if (instruccion == 'f') detenerse();
          if (instruccion == 'g') intermitente_double(led_der, led_izq);
          if (instruccion == 'h') intermitente_single(led_der);
          if (instruccion == 'i') intermitente_single(led_izq); 
          if (instruccion == 'j') borrar_estados();
          if (instruccion == 'k') apagar_direccional(led_der); // Apaga la direccional derecha
          if (instruccion == 'l') apagar_direccional(led_izq); // Apaga la direccional izquierda
          if (instruccion == 'm') apagar_direccionales();
          
          delay(10);                                              //Chance para que recepcione
}

// Metodos complementarios para traducir la direccion enviada al bluetooth
void resetData()
{
   for (int i=0; i<=pos; i++) buffer[i] = 0; 
   pos = 0;
}

int buffer2int(char* buffer){
    int i;
    sscanf(buffer, "%d", &i);
    return i;
    
  }

// Se encarga de realizar una rutina de movimientos
void batch ()
{
  Serial.println("Batch");               
  avanzar();
  delay(3000);
  detenerse();
  delay(1500);
  
  reversa();
  delay(3000);
  detenerse();
  delay(1500);
  
  derecha();
  delay(3000);
  detenerse();
  delay(1500);
  
  izquierda();
  delay(3000);
  detenerse();
  delay(1500);
  
  digitalWrite(led_back, LOW);   
  intermitente_single(led_der);                   
  intermitente_single(led_der);             
  intermitente_single(led_der);                 
  apagar_direccional(led_der);
  delay(1500);

  digitalWrite(led_back, LOW);   
  intermitente_single(led_izq);                    
  intermitente_single(led_izq);   
  intermitente_single(led_izq);  
  apagar_direccional(led_izq);
  delay(1500);
  
  intermitente_double(led_der,led_izq);    
  intermitente_double(led_der,led_izq);    
  intermitente_double(led_der,led_izq);    
  apagar_direccionales();    
  delay(2000);
  
  detenerse();
  delay(3000);
  
}

// Activa dos luces intermitentes que recibe como parametro
void intermitente_double(int led_pin1, int led_pin2)
{
  Serial.print("Encendiendo direccionales");
   digitalWrite(led_pin1, HIGH);
   digitalWrite(led_pin2, HIGH);
   delay (350);
   digitalWrite(led_pin1, LOW);
   digitalWrite(led_pin2, LOW);
   delay (350);
 }

// Enciende una luz intermitente
void intermitente_single (int led_pin)
{
   Serial.print("Encendiendo direccional");
   digitalWrite(led_pin, HIGH);
   delay (350);
   digitalWrite(led_pin, LOW);
   delay (350);
}

// Manda al carro hacia el frente.
void avanzar()                     
{      
   Serial.println("Avanzar");   
   digitalWrite(led_back, LOW);
   analogWrite(derA, vel);    
   analogWrite(izqA, vel);
   analogWrite(derB, 0);  
   analogWrite(izqB, 0);    
}

// Envia al carro a la derecha
void izquierda()           
{
    Serial.println("Izquierda");
    digitalWrite(led_back, LOW);
    analogWrite(derB, vel);     
    analogWrite(izqB, 0); 
    analogWrite(derA, 0);  
    analogWrite(izqA, vel);      
}

// Detiene al carro  
void detenerse()                  
{  
    Serial.println("Detenerse");
    analogWrite(derB, 0);     
    analogWrite(izqB, 0); 
    analogWrite(derA, 0);    
    analogWrite(izqA, 0);
    digitalWrite(led_der, LOW);
    analogWrite(led_izq, LOW);
    digitalWrite(led_back, HIGH);  
}

// El carro se dirige hacia la derecha
void derecha()       
{
    Serial.println("Derecha");
    digitalWrite(led_back, LOW);
    analogWrite(derB, 0);     
    analogWrite(izqB, vel);
    analogWrite(izqA, 0);
    analogWrite(derA, vel);  
} 

// El carro va hacia atras
void reversa ()
{             
    Serial.println("Reversa") ;                       
    apagar_direccionales(); 
    digitalWrite(led_back, HIGH);
    analogWrite(derA, 0);     
    analogWrite(izqA, 0); 
    analogWrite(derB, vel);
    analogWrite(izqB, vel);       
}

void borrar_estados()
{
    Serial.println("Borrando Estados");
    analogWrite(derB, 0);     
    analogWrite(izqB, 0); 
    analogWrite(derA, 0);    
    analogWrite(izqA, 0);
    digitalWrite(led_der, LOW);
    analogWrite(led_izq, LOW);
    digitalWrite(led_back, LOW);  
}

void apagar_direccional (int led_pin)
{
    Serial.print("Apagando direccional"); 
    digitalWrite(led_pin, LOW);  
    delay(750);
}


void apagar_direccionales()
{
      Serial.println("Apagando Direccionales Estados");
      digitalWrite(led_der, LOW);
      digitalWrite(led_izq, LOW);
}


