#include <string>
#include "MOMInterface.h"
#include "Msj.h"

using namespace std;

class MOM : public MOMInterface
{
    private: 
    map<string, queue<Msj>> listaColas;
    map<string, string> declaradorDeCola;

    public:
    MOM(){
    }

    void declararCola(const string &nombreCola, const string &invocador) override {
        vector<string> key, colas;
        for(map<string,string>::iterator it = declaradorDeCola.begin(); it != declaradorDeCola.end(); ++it) {
            key.push_back(it->first);
        }
        int contieneInvocador = count(key.begin(), key.end(), invocador);
        if(contieneInvocador > 0){
            print("El invocador ya tiene una cola declarada");
        }
        else{
            declaradorDeCola.insert({invocador, nombreCola});
            for(map<string,queue<Msj>>::iterator it = listaColas.begin(); it != listaColas.end(); ++it) {
                colas.push_back(it->first);
            }
            int colaExiste = count(colas.begin(), colas.end(), nombreCola);
            if(colaExiste = 0){
                queue<Msj> nuevaCola;
                listaColas.insert({nombreCola, nuevaCola});
                print("Se ha declarado una nueva cola");
            }
            else{
                print("La cola ya habia sido declarada");
            }
        
        }
    }
};