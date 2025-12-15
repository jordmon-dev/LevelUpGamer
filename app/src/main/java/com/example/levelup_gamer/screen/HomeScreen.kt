package com.example.levelup_gamer.screen

import androidx.compose.foundation.Image // Importante para cargar imágenes locales
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource // Necesario para R.drawable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.levelup_gamer.R // Verifica que este import sea correcto
import com.example.levelup_gamer.viewmodel.UsuarioViewModel
import com.example.levelup_gamer.viewmodel.CarritoViewModel
import com.example.levelup_gamer.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    productoViewModel: ProductoViewModel,
    carritoViewModel: CarritoViewModel,
    viewModel: UsuarioViewModel = viewModel()
) {
    val usuarioState by viewModel.usuarioState.collectAsState()

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0A0A0A),
            Color(0xFF1A1A2E),
            Color(0xFF16213E)
        )
    )

    // ✅ LISTA CON RECURSOS LOCALES (R.drawable.xxx)
    val featuredGames = listOf(
        Game("Cyberpunk 2077", "PS5, Xbox, PC", 49990, R.drawable.cyberpunk),
        Game("The Witcher 3", "PC, Switch", 29990, R.drawable.witcher3),
        Game("Red Dead 2", "PS4, Xbox", 39990, R.drawable.reddead2),
        Game("The Last of Us II", "PS4", 45990, R.drawable.tlou2),
        Game("Elden Ring", "PS5, Xbox, PC", 54990, R.drawable.eldenring),
        Game("GTA V", "PS4, Xbox, PC", 22990, R.drawable.gta5)
    )

    val categorias = listOf("PlayStation", "Xbox", "Nintendo Switch", "PC", "VR", "Mobile")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // --- HEADER ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(16.dp, RoundedCornerShape(20.dp), clip = true),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1E1E2E).copy(alpha = 0.9f)
                ),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            val nombreMostrar = if (usuarioState.nombre.isNotEmpty()) {
                                usuarioState.nombre.split(" ").firstOrNull() ?: "Gamer"
                            } else {
                                "Invitado"
                            }

                            Text(
                                "¡Hola, $nombreMostrar!",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "Level-Up Gamer",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFA0A0A0)
                            )
                        }
                        IconButton(
                            onClick = { navController.navigate("perfil") },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color(0xFF00FF88))
                        ) {
                            Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.Black)
                        }
                    }

                    if (usuarioState.email.endsWith("@duocuc.cl")) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF00FF88).copy(alpha = 0.2f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.School, contentDescription = "Estudiante", tint = Color(0xFF00FF88))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "20% descuento estudiante Duoc activo",
                                    color = Color(0xFF00FF88),
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            // --- BANNER DESTACADO (Usa Cyberpunk local) ---
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(160.dp)
                    .shadow(16.dp, RoundedCornerShape(20.dp), clip = true),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF2D1B69)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Imagen de fondo local
                    Image(
                        painter = painterResource(id = R.drawable.cyberpunk),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize().alpha(0.6f)
                    )

                    Column(
                        modifier = Modifier.fillMaxSize().padding(20.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("NUEVO LANZAMIENTO", style = MaterialTheme.typography.labelSmall, color = Color(0xFF00FF88), fontWeight = FontWeight.Bold)
                            Text("Cyberpunk 2077\nUltimate Edition", style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Bold, lineHeight = 24.sp)
                        }
                        Button(
                            onClick = { navController.navigate("catalogo") },
                            modifier = Modifier.height(40.dp).clip(RoundedCornerShape(12.dp)),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FF88), contentColor = Color.Black)
                        ) {
                            Text("VER CATÁLOGO", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Categorías
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Categorías Destacadas", style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(categorias) { category -> CategoryCard(category = category) }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Más Vendidos
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Más Vendidos", style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.height(500.dp)
                ) {
                    items(featuredGames) { game ->
                        GameCard(game = game, onClick = { navController.navigate("catalogo") })
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Soporte
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Información y Soporte", style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
                NavigationCard(Icons.Default.Info, "Acerca de", "Conoce más sobre Level-Up Gamer", { navController.navigate("about") }, Color(0xFFFF6B6B))
                NavigationCard(Icons.Default.Help, "Centro de Ayuda", "Soporte técnico y preguntas frecuentes", { navController.navigate("ayuda") }, Color(0xFF00FF88))
                NavigationCard(Icons.Default.ShoppingCart, "Mi Carrito", "Revisa tus productos seleccionados", { navController.navigate("carrito") }, Color(0xFFFFA726))
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// --- COMPONENTES AUXILIARES ---

@Composable
fun CategoryCard(category: String) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(80.dp)
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .clickable { /* Acción */ },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A3E)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(category, style = MaterialTheme.typography.bodyMedium, color = Color.White, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun GameCard(game: Game, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp) // Altura ajustada para la imagen
            .shadow(8.dp, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A3E)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ✅ AQUI CAMBIÓ: Usamos Image con painterResource
            Image(
                painter = painterResource(id = game.imagenRes), // Carga desde R.drawable
                contentDescription = game.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp) // Imagen arriba
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = game.platforms,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFA0A0A0),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$ ${game.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF00FF88),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun NavigationCard(icon: ImageVector, title: String, description: String, onClick: () -> Unit, color: Color) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A3E)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, style = MaterialTheme.typography.bodyLarge, color = Color.White, fontWeight = FontWeight.Medium)
                Text(description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Icon(Icons.Default.ChevronRight, contentDescription = "Ir", tint = Color.Gray, modifier = Modifier.size(20.dp))
        }
    }
}

// ✅ MODELO LOCAL (Actualizado para usar Int)
data class Game(
    val title: String,
    val platforms: String,
    val price: Int,
    val imagenRes: Int // Cambiado de String a Int
)