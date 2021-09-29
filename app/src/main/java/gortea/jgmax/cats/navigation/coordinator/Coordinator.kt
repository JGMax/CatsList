package gortea.jgmax.cats.navigation.coordinator

interface Coordinator {
    fun popBackStack()
    fun navigateToList()
    fun navigateToFullView(url: String)
}