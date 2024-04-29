#include <string>
using namespace std;

class Msj {
    private: 
    int prioridad;
    int caducidad;
    string mensaje;

    public:
    Msj(int prioridad, int caducidad, string mensaje){
        this->prioridad = prioridad;
        this->caducidad = caducidad;
        this->mensaje = mensaje;
    }

    // Getter para obtener la prioridad
    int getPrioridad() const {
        return prioridad;
    }

    // Setter para establecer la prioridad
    void setPrioridad(int nuevaPrioridad) {
        prioridad = nuevaPrioridad;
    }

    // Getter para obtener la caducidad
    int getCaducidad() const {
        return caducidad;
    }

    // Setter para establecer la caducidad
    void setCaducidad(int nuevaCaducidad) {
        caducidad = nuevaCaducidad;
    }

    // Getter para obtener el mensaje
    string getMensaje() const {
        return mensaje;
    }

    // Setter para establecer el mensaje
    void setMensaje(const string& nuevoMensaje) {
        mensaje = nuevoMensaje;
    }
};