data "aws_iam_policy_document" "rss_handler_assume_role" {
  statement {
    actions = ["sts:AssumeRole"]

    principals {
      type        = "Service"
      identifiers = ["lambda.amazonaws.com"]
    }
  }
}

resource "aws_iam_role" "rss_handler" {
  name               = var.rss_lambda_function_name
  assume_role_policy = data.aws_iam_policy_document.rss_handler_assume_role.json
}

resource "aws_lambda_function" "rss_handler" {
  function_name = var.rss_lambda_function_name
  filename      = "${path.module}/data/serverless-rss-reader-placeholder-1.0.0.jar"
  role          = aws_iam_role.rss_handler.arn
  handler       = "com.arkinmodi.rssreader.aws.DiscordHandler::handleRequest"
  runtime       = "java17"
  timeout       = 60
}

resource "aws_lambda_function_url" "rss_handler" {
  function_name      = var.rss_lambda_function_name
  authorization_type = "AWS_IAM"
}

resource "aws_cloudwatch_log_group" "rss_handler" {
  name              = "/aws/lambda/${var.rss_lambda_function_name}"
  retention_in_days = 14
}

data "aws_iam_policy_document" "rss_handler_lambda_logging" {
  statement {
    actions = [
      "logs:CreateLogGroup",
      "logs:CreateLogStream",
      "logs:PutLogEvents",
    ]
    resources = ["arn:aws:logs:*:*:*"]
  }
}

resource "aws_iam_role_policy" "rss_handler" {
  role   = aws_iam_role.rss_handler.name
  policy = data.aws_iam_policy_document.rss_handler_lambda_logging.json
}
