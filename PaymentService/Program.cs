using System.Text;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using PaymentService.Data;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

// =========================================================
// DATABASE
// =========================================================

builder.Services.AddSingleton<PaymentDbContext>();

// =========================================================
// HTTP CLIENT
// =========================================================

builder.Services.AddHttpClient();

// =========================================================
// PAYMENT SERVICE
// =========================================================

builder.Services.AddScoped<
    PaymentService.Services.PaymentService>();

// =========================================================
// JWT AUTHENTICATION
// =========================================================

var jwtSecret =
    builder.Configuration["Jwt:Secret"];

if (string.IsNullOrWhiteSpace(jwtSecret))
{
    throw new Exception(
        "Jwt:Secret is missing from appsettings.json");
}

var key =
    new SymmetricSecurityKey(
        Encoding.UTF8.GetBytes(jwtSecret));

builder.Services
    .AddAuthentication(
        JwtBearerDefaults.AuthenticationScheme)
    .AddJwtBearer(options =>
    {
        options.TokenValidationParameters =
            new TokenValidationParameters
            {
                ValidateIssuerSigningKey = true,

                IssuerSigningKey = key,

                ValidateIssuer = false,

                ValidateAudience = false,

                ValidateLifetime = true,

                ClockSkew = TimeSpan.Zero,

                NameClaimType = "sub",

                RoleClaimType = "user_role"
            };
    });

// =========================================================
// AUTHORIZATION
// =========================================================

builder.Services.AddAuthorization();

// =========================================================
// CORS
// =========================================================

builder.Services.AddCors(options =>
{
    options.AddPolicy("Frontend", policy =>
    {
        policy
            .AllowAnyOrigin()
            .AllowAnyHeader()
            .AllowAnyMethod();
    });
});

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseCors("Frontend");

// IMPORTANT
app.UseAuthentication();

app.UseAuthorization();

app.MapControllers();

app.Run();