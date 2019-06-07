/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author luiz.ompjunior
 */
public class Fuzzy {
    
    double distancia, pontuacao;
    float pertPoucaDist, pertMediaDist, pertAltaDist;
    float pertPoucaPont, pertMediaPont, pertAltaPont;
    float pertPoucaDif, pertMediaDif, pertAltaDif;

    public Fuzzy() {
        
    }

    public void fuzzyFicarPontuacao(int pont){
        pertPoucaPont = poucaPontuacao(pont);
        pertMediaPont = mediaPontuacao(pont);
        pertAltaPont = altaPontuacao(pont);
    }
    
    public void fuzzyFicarDistancia(float dist){
        pertPoucaDist = poucaDist(dist);
        pertMediaDist = mediaDist(dist);
        pertAltaDist = altaDist(dist);
    }
    
    public void inferenciaFuzzy(){
        
        if(pertPoucaDist >0 && pertPoucaPont >0){
            pertPoucaDif = (pertPoucaDist+pertPoucaPont)/2;
            
        }
        
        if(pertPoucaDist >0 && pertMediaPont >0){
            pertPoucaDif = (pertPoucaDist+pertMediaPont)/2;
            
        }
        
        if(pertPoucaDist >0 && pertAltaPont >0){
            pertPoucaDif = (pertPoucaDist+pertAltaPont)/2;
            
        }
        
         if(pertMediaDist >0 && pertPoucaPont >0){
            pertPoucaDif = (pertMediaDist+pertPoucaPont)/2;
            
        }
        
        if(pertMediaDist >0 && pertMediaPont >0){
            pertPoucaDif = (pertMediaDist+pertMediaPont)/2;
            
        }
        
        if(pertMediaDist >0 && pertAltaPont >0){
            pertPoucaDif = (pertMediaDist+pertAltaPont)/2;
            
        }
        
         if(pertAltaDist >0 && pertPoucaPont >0){
            pertPoucaDif = (pertAltaDist+pertPoucaPont)/2;
            
        }
        
        if(pertAltaDist >0 && pertMediaPont >0){
            pertPoucaDif = (pertAltaDist+pertMediaPont)/2;
            
        }
        
        if(pertAltaDist >0 && pertAltaPont >0){
            pertPoucaDif = (pertAltaDist+pertAltaPont)/2;
            
        }
        
    }
    
    
    public float desfuzzyficar(){

        return ((pertPoucaDif * areaDeBaixaDificuldade())+ (pertMediaDif*areaDeMediaDificuldade()) + (pertAltaDif*areaDeAltaDificuldade())) / (areaDeBaixaDificuldade()+areaDeMediaDificuldade()+areaDeAltaDificuldade());
    }
    
    
    
    public float areaDeBaixaDificuldade(){
        float pontuacao = -pertPoucaPont*100-200;
        float baseMaior, baseMenor;
        baseMaior = 200;
        baseMenor = pontuacao;
        
        return  (baseMenor+baseMaior)*pertPoucaPont/2;
    }
    
    public float areaDeMediaDificuldade(){
        float subida = -pertMediaPont*200+300;
        float descida = -pertMediaPont*200+500;
        
        float base = 500-100;
        float altura = pertMediaPont;
        
        return base*altura/2;
    }
    
    public float areaDeAltaDificuldade(){
        
        float pontuacao = -pertAltaPont*200+600;
        float baseMaior = 600-400;
        float baseMenor = 600-pontuacao;
        
        return (baseMaior+baseMenor)*pertAltaPont/2;
    }
    
    
    
    
    
    public float poucaDist(float dist){
        if(dist <= 2){
            return 1.0f;        
        }else if(dist > 2 && dist <= 4){
            return (4-dist)/2;
        }else{
            return 0.0f;
        } 
    }
    
    public float mediaDist(float dist){
        if(dist <=3 && dist < 6){
            return(dist-3)/3;
        }else if(dist >=6 && dist < 8){
            return(dist - 6)/2;
        }else{
            return 0.0f;
        }
    }
    
    public float altaDist(float dist){
        if(dist >= 5 && dist < 10){
            return(dist - 5)/5;
        }else if(dist >= 10){
            return 1.0f;
        }else{
            return 0.0f;
        }
    }
    
    
    
    
    
    
    public float poucaPontuacao(int pont){
        if(pont < 100){
            return 1.0f;  
        }else if(pont >= 100 && pont < 200){
            return (200 - pont)/100;
        }else{
            return 0.0f;
        }
    }
    
    public float mediaPontuacao(int pont){
        if(pont >= 100 && pont < 300 ){
            return (pont - 100)/200;
        }else if(pont >= 300 && pont < 500){
            return (500 - pont)/200;
        }else{
            return 0.0f;
        }
    }
    
    public float altaPontuacao(int pont){
        if(pont >= 400 && pont < 600){
            return(pont-400)/200;
        }else if(pont >= 600){
            return 1.0f;
        }else{
            return 0.0f;
        }  
    }
    
}
