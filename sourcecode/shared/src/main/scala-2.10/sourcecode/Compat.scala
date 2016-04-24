package sourcecode

object Compat{
  type Context = scala.reflect.macros.Context
  def enclosingOwner(c: Context) = {
    c.asInstanceOf[scala.reflect.macros.runtime.Context]
      .callsiteTyper
      .context
      .owner
      .asInstanceOf[c.Symbol]
  }

  def enclosingParamList(c: Context): List[List[c.Symbol]] = {
    def nearestClassOrMethod(owner: c.Symbol): c.Symbol =
      if (owner.isMethod || owner.isClass) owner else nearestClassOrMethod(owner.owner)

    val com = nearestClassOrMethod(enclosingOwner(c))
    if (com.isClass) List() else com.asMethod.paramss
  }
}