#include <iostream>
#include <string>

using namespace std;
/*******************************************************************************
 * Authors: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 *
 * File: MOMInterface.cpp
 * Comments: This is the file corresponding to the interface for clients of the
 * broker.
 ******************************************************************************/

/**
 * Definition of the remote interface MOMInterface
 */
class MOMInterface {
    public:
    
    // declararCola
    virtual void declararCola(const string &nombre, const string &invocador) = 0;

    // publicar
    virtual void publicar(const string &nombreCola, const void *mensaje, int prioridad) = 0;

    // consumir
    virtual void consumir(const string &nombreConsumidor, const string &hostRemoto) = 0;

    // suscribirse
    virtual void suscribirse(const string& nombreCola, const string &nombreConsumidor) = 0;
};
