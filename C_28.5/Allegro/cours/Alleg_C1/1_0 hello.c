/********************************************************
Initialisation d'allegro et fen�tre de message popup
********************************************************/

#include <allegro.h>

int main(int argc, char *argv[]) {
	allegro_init();
	allegro_message("hello world allegro !");
	return 0;
}
END_OF_MAIN();
