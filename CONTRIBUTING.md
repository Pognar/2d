# Contributing to 2D RPG Engine

Thank you for your interest in contributing to the 2D RPG Engine project! This document provides guidelines and information for contributors.

## Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally
3. **Set up the development environment** (see [DEVELOPMENT.md](docs/DEVELOPMENT.md))
4. **Create a feature branch** from `main`
5. **Make your changes** with appropriate tests
6. **Submit a pull request**

## Development Workflow

### Branch Naming
- `feature/description` - New features
- `fix/description` - Bug fixes
- `docs/description` - Documentation updates
- `refactor/description` - Code refactoring

### Commit Messages
Follow the conventional commit format:
```
type(scope): description

[optional body]

[optional footer]
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting, etc.)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Maintenance tasks

**Examples:**
```
feat(ecs): add component querying system
fix(render): resolve sprite batching memory leak
docs(api): update component documentation
test(collision): add unit tests for AABB detection
```

## Code Standards

### Java Style Guide

**Naming Conventions:**
- Classes: `PascalCase` (e.g., `GameEngine`, `RenderSystem`)
- Methods/Variables: `camelCase` (e.g., `updatePosition`, `deltaTime`)
- Constants: `UPPER_SNAKE_CASE` (e.g., `MAX_ENTITIES`, `DEFAULT_FPS`)
- Packages: `lowercase` (e.g., `engine.core`, `game.entities`)

**Code Structure:**
```java
public class ExampleClass {
    // Constants first
    private static final int MAX_SIZE = 100;
    
    // Fields
    private final String name;
    private int value;
    
    // Constructor
    public ExampleClass(String name) {
        this.name = name;
    }
    
    // Public methods
    public void doSomething() {
        // Implementation
    }
    
    // Private methods
    private void helperMethod() {
        // Implementation
    }
}
```

**Java 21 Best Practices:**
- Use records for immutable data structures
- Leverage sealed classes for type safety
- Apply pattern matching where appropriate
- Utilize virtual threads for I/O operations

### Documentation

**Javadoc Requirements:**
- All public classes and methods must have Javadoc
- Include `@param`, `@return`, and `@throws` where applicable
- Provide usage examples for complex APIs

```java
/**
 * Manages entity lifecycle and component storage.
 * 
 * @since 1.0
 */
public class World {
    /**
     * Creates a new entity with a unique identifier.
     * 
     * @return newly created entity
     * @throws IllegalStateException if maximum entity limit reached
     */
    public Entity createEntity() {
        // Implementation
    }
}
```

## Testing Guidelines

### Unit Tests
- Write tests for all public methods
- Use descriptive test method names
- Follow AAA pattern (Arrange, Act, Assert)
- Mock external dependencies

```java
@Test
void shouldUpdateEntityPositionWithVelocity() {
    // Arrange
    var entity = world.createEntity();
    entity.add(new PositionComponent(0, 0));
    entity.add(new VelocityComponent(10, 5));
    
    // Act
    movementSystem.update(1.0f);
    
    // Assert
    var position = entity.get(PositionComponent.class);
    assertThat(position.x()).isEqualTo(10);
    assertThat(position.y()).isEqualTo(5);
}
```

### Integration Tests
- Test system interactions
- Verify game state transitions
- Test asset loading and saving

### Test Coverage
- Maintain 80%+ code coverage
- Focus on critical paths and edge cases
- Use coverage reports to identify gaps

## Pull Request Process

### Before Submitting
- [ ] Code follows style guidelines
- [ ] All tests pass locally
- [ ] Documentation is updated
- [ ] No merge conflicts with main branch
- [ ] Commit messages follow convention

### Pull Request Template
```markdown
## Description
Brief description of changes made.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Performance improvement
- [ ] Code refactoring

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests pass
- [ ] Manual testing completed

## Checklist
- [ ] Code follows project style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] No breaking changes (or documented)
```

### Review Process
1. **Automated checks** must pass (build, tests, linting)
2. **Code review** by at least one maintainer
3. **Testing** on multiple platforms if applicable
4. **Approval** and merge by maintainer

## Issue Reporting

### Bug Reports
Use the bug report template and include:
- Steps to reproduce
- Expected vs. actual behavior
- Environment details (OS, Java version)
- Relevant logs or screenshots

### Feature Requests
Use the feature request template and include:
- Problem description
- Proposed solution
- Alternative solutions considered
- Additional context

### Questions
For questions about usage or development:
- Check existing documentation first
- Search existing issues
- Use discussion forums for general questions
- Create issues for specific technical questions

## Code of Conduct

### Our Standards
- Be respectful and inclusive
- Focus on constructive feedback
- Help others learn and grow
- Maintain professional communication

### Unacceptable Behavior
- Harassment or discrimination
- Trolling or inflammatory comments
- Personal attacks
- Publishing private information

### Enforcement
Violations may result in temporary or permanent bans from the project. Report issues to project maintainers.

## Recognition

Contributors will be recognized in:
- `CONTRIBUTORS.md` file
- Release notes for significant contributions
- Project documentation where appropriate

## Questions?

If you have questions about contributing:
- Check the [documentation](docs/)
- Search existing [issues](../../issues)
- Create a new issue with the "question" label
- Contact maintainers directly

Thank you for contributing to the 2D RPG Engine!
